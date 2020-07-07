package com.campaign.admission.controller.filter;

import com.campaign.admission.domain.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthFilter implements Filter {

    private final Map<String, Role> rolesAllowedURIs = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        rolesAllowedURIs.put("/admission/api/student", Role.STUDENT);
        rolesAllowedURIs.put("/admission/api/student/subjects", Role.STUDENT);
        rolesAllowedURIs.put("/admission/api/student/exams", Role.STUDENT);
        rolesAllowedURIs.put("/admission/api/student/specialties", Role.STUDENT);
        rolesAllowedURIs.put("/admission/api/student/specialty", Role.STUDENT);
        rolesAllowedURIs.put("/admission/api/student/specialty/apply", Role.STUDENT);
        rolesAllowedURIs.put("/admission/api/student/rating", Role.STUDENT);
        rolesAllowedURIs.put("/admission/api/student/results", Role.STUDENT);
        rolesAllowedURIs.put("/admission/api/admin", Role.ADMIN);
        rolesAllowedURIs.put("/admission/api/admin/set_admission", Role.ADMIN);
        rolesAllowedURIs.put("/admission/api/admin/subject", Role.ADMIN);
        rolesAllowedURIs.put("/admission/api/admin/subject/save_marks", Role.ADMIN);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        Role role = (Role) httpRequest.getSession().getAttribute("role");
        Role allowedRole = rolesAllowedURIs.get(httpRequest.getRequestURI());
        if (allowedRole == null || allowedRole == role) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("redirect:/admission/api/home?" +
                    httpRequest.getQueryString());
        }
    }

    @Override
    public void destroy() {
    }
}
