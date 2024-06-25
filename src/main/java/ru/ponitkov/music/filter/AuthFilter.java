package ru.ponitkov.music.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ponitkov.music.util.UrlPathGetter;

import java.io.IOException;
import java.util.List;

import static ru.ponitkov.music.util.AttributeGetter.MUSIC_ARTIST;
import static ru.ponitkov.music.util.UrlPathGetter.LOGIN_URL;
import static ru.ponitkov.music.util.UrlPathGetter.REGISTRATION_URL;
import static ru.ponitkov.music.util.UrlPathGetter.getFullPath;

@WebFilter("/*")
public class AuthFilter implements Filter {
    private static final List<String> PUBLIC_URI_PATH = List.of(LOGIN_URL, REGISTRATION_URL);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var uri = ((HttpServletRequest) servletRequest).getRequestURI();

        if (isPublishUri(uri) || isUser(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect(getFullPath(LOGIN_URL));
        }
    }

    private boolean isUser(ServletRequest request) {
        return ((HttpServletRequest) request).getSession().getAttribute(MUSIC_ARTIST) != null;
    }

    private boolean isPublishUri(String uri) {
        return PUBLIC_URI_PATH.stream().map(UrlPathGetter::getFullPath).anyMatch(uri::startsWith);
    }
}
