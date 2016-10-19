package ch2.pyrmont;

public class Constants {

    public static final String WEB_ROOT      = Constants.class.getClassLoader().getResource("").getPath() + "webroot";

    //public static final String SERVLET_ROOT  = System.getProperty("user.dir") + "\\src\\main\\java\\ch2\\pyrmont\\servlet";
    public static final String SERVLET_ROOT  = Constants.class.getClassLoader().getResource("").getPath() + "ch2/pyrmont/servlet/";
}