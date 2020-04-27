package com.campaign.admission.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class PaginationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        if (httpRequest.getParameter("page") != null) {
            int page = parseInt(httpRequest.getParameter("page"));
            String redirectURI = httpRequest.getRequestURI() + httpRequest.getQueryString();
            if (!httpRequest.getQueryString().contains("page=" + page)) {

            } else {
                ((HttpServletRequest) servletRequest).getSession().setAttribute("page", page);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
