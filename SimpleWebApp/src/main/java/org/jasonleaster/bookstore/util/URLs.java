package org.jasonleaster.bookstore.util;

public class URLs {
    private URLs(){}

    // first level URLs for login page
    public final static String ROOT     = "/";
    public final static String LOGIN    = "/login";
    public final static String LOGOUT   = "/logout";
    public final static String REGISTER = "/register";
    
    // first level URLs for home page
    public final static String HOMEPAGE = "/home";

    // first level URLs for user --
    public final static String USERS = "/users";
    public final static String BOOKS = "/books";
    public final static String RECORDS = "/records";

    // second level URLs for operation
    public final static String CREATE = "/create";
    public final static String QUERY  = "/query";
    public final static String SEARCH = "/search";
    public final static String MODIFY = "/modify";
    public final static String DELETE = "/delete";
    public final static String UPDATE = "/update";

    // Only for books
    public final static String DOWNLOAD = "/download";

    // URL for RESTful API.
    public final static String API = "/api";

    // static resources
    public final static String PATH_STATIC  = "/static/";
}
