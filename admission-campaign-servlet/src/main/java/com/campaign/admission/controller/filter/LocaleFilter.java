package com.campaign.admission.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.String.format;

public class LocaleFilter implements Filter {

    private static final String DEFAULT_LOCALE_PARAMETER = "?locale=en";
    private static final String QUERY_STRING = "%s&%s";
    private static final Map<String, Locale> LOCALES = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOCALES.put("en", new Locale("en"));
        LOCALES.put("ua", new Locale("ua"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        Locale locale = LOCALES.get(httpRequest.getParameter("locale"));
        if(locale == null) {
            String redirectURI = httpRequest.getRequestURI() + DEFAULT_LOCALE_PARAMETER;
            if(httpRequest.getParameterMap().size() > 0) {
                redirectURI = format(QUERY_STRING, redirectURI, httpRequest.getQueryString());
            }
            ((HttpServletResponse) servletResponse).sendRedirect(redirectURI);
            return;
        } else {
            ((HttpServletRequest) servletRequest).getSession().setAttribute("locale", locale);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
