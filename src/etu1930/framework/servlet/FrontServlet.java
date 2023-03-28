package etu1930.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import etu1930.framework.Mapping;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontServlet extends HttpServlet{
    HashMap<String, Mapping> urlMapping;
    protected void processRequest(HttpServletRequest req, HttpServletResponse rep) {
        rep.setContentType("text/plain");
        try (PrintWriter out = rep.getWriter()){
            out.println(req.getRequestURI());
            out.println(getUrl(req));
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
}
