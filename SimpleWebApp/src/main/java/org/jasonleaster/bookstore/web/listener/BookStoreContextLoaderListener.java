package org.jasonleaster.bookstore.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class BookStoreContextLoaderListener implements ServletContextListener{

    @SuppressWarnings("unused")
	@Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        System.out.println("XXXXXXXXXXX----Context loaded----XXXXXXXXXXXXXX");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("XXXXXXXXXXX----Context destroy----XXXXXXXXXXXXXX");
    }
}
