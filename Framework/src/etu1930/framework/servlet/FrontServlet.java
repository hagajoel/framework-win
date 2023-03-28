package etu1930.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import etu1930.framework.Mapping;
import etu1930.framework.annotation.Url;
import etu1930.framework.util.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontServlet extends HttpServlet{
    HashMap<String, Mapping> urlMapping;

    public void addMappingUrls(Class  c) {
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            Url[] a = method.getAnnotationsByType(Url.class);
            if (a.length > 0) {
                // Get mapping urls
                getUrlsMaping().put(a[0].value(), new Mapping(c.getSimpleName(), method.getName()));
            }
        }
    }

    // Get Mapping urls
    public HashMap<String, Mapping> getUrlsMaping() {
        return urlMapping;
    }

    //Set Mapping Urls
    public void setUrlsMapping (HashMap<String, Mapping> mappingUrls) {
        this.urlMapping = mappingUrls;
    }

    public void setUrlsMapping (String path) {
        try {
            List<Class> lc = Utils.getClassFrom(path);
            setUrlsMapping(new HashMap<String, Mapping>());

            for (Class c : lc) {
                for (Method m  : c.getDeclaredMethods()) {
                    Url u = m.getAnnotation(Url.class);
                    if (u != null) {
                        getUrlsMaping().put(u.value(), new Mapping(c.getSimpleName(), m.getName()));
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    // init

    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        setUrlsMapping("etu1930.model");
    }

    // Process request
    protected void processRequest(HttpServletRequest req, HttpServletResponse rep) {
        rep.setContentType("text/plain");
        try (PrintWriter out = rep.getWriter()){
            out.println(req.getRequestURI());
            out.println(getUrl(req));
            //Get Method from Url

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        processRequest(req, resp);
    }

    public String getUrl(HttpServletRequest req) {
        String result;
        String contextPath = req.getContextPath();
        String url = req.getRequestURI();
        result = url.split(contextPath)[1];
        String query = req.getQueryString();
        if (query != null) {
            result = result.concat("?" + query);
        }
        return result;
    }

    // Get method from url
    public Method getMethodFromUrl(String url) throws Exception {
        List<Class> listClass = Utils.getClassFrom("etu1930.model");
        for (Class c : listClass) {
            if (c.getSimpleName() == getUrlsMaping().get(url).getClassName()) {
                for (Method m  : c.getDeclaredMethods()) {
                    if (m.getName() == getUrlsMaping().get(url).getMethod()) {
                        return m;
                    }
                }
            }
        }
        throw new Exception("Method Not Found");
    }
}
