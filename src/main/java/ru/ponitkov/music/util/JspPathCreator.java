package ru.ponitkov.music.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspPathCreator {
    private static final String JSP_DEFAULT_PATH = "/WEB-INF/jsp/%s.jsp";
    private static final String JSP_LOGIN_PATH = "/WEB-INF/jsp/auth/%s.jsp";

    public static String getDefaultJspPath(String jsp) {
        return JSP_DEFAULT_PATH.formatted(jsp);
    }

    public static String getLoginJspPath(String jsp) {
        return JSP_LOGIN_PATH.formatted(jsp);
    }
}
