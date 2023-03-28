package etu1930.framework.util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import etu1930.framework.Mapping;
import etu1930.framework.annotation.Url;

public class Utils {
    public static List<Class> getClassFrom(String packages) throws Exception {
        String path = packages.replace("[.]", "/");
        URL packageUrl = Thread.currentThread().getContextClassLoader().getResource(path);

        File packageDirectory = new File(packageUrl.toURI());
        File[] in = packageDirectory.listFiles();
        List<Class> lists = new ArrayList<>();

        for (File f : in) {
            String c = packages + "." + f.getName().substring(0, f.getName().lastIndexOf("."));
            lists.add(Class.forName(c));
        }
        return lists;
    }

    public static HashMap<String, Mapping> getMappingUrls(Class<?> c) {
        HashMap<String, Mapping> mappingUrls = new HashMap<>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            Url[] a = method.getAnnotationsByType(Url.class);
            if (a.length > 0) {
                mappingUrls.put(a[0].value(), new Mapping(c.getSimpleName(), method.getName()));
            }
        }
        return mappingUrls;
    }
}
