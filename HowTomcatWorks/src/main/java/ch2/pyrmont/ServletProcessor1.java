package ch2.pyrmont;

import java.net.URL;
import java.net.URLClassLoader;
import java.io.File;
import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServletProcessor1 {

    public void process(Request request, Response response) {

        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        URLClassLoader loader = null;

        try {
            // create a URLClassLoader
            File servletClassPath = new File(Constants.SERVLET_ROOT);
            URL url    = servletClassPath.toURI().toURL();
            URL[] urls = new URL[] {url};

            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            System.out.println(e.toString() );
        }

        Class myClass = null;
        try {
            /*
            * The Java ClassLoader.loadClass(String) method requires that
            * class names must be fully qualified by their package and class name
            *
            * Error eg: myClass = loader.loadClass(servletName);
            * */
            myClass = loader.loadClass("ch2.pyrmont.servlet." + servletName);

        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }

        Servlet servlet = null;

        try {
            servlet = (Servlet) myClass.newInstance();
            servlet.service((ServletRequest) request, (ServletResponse) response);
        } catch (Exception e) {
            System.out.println(e.toString());
        } catch (Throwable e) {
            System.out.println(e.toString());
        }

    }
}