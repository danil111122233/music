package ru.ponitkov.music.util;

public class UrlPathGetter {
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String REGISTRATION_URL = "/registration";
    public static final String ACCOUNT_URL = "/account";

    public static String getFullPath(String path) {
        return "/music" + path;
    }
}
