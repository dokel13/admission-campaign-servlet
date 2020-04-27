package com.campaign.admission.controller.command;

import com.campaign.admission.domain.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RatingCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");

        if (role == Role.STUDENT) {
            return "/WEB-INF/jsp/student/rating.jsp";
        } else if (role == Role.ADMIN) {
            return "/WEB-INF/jsp/admin/rating.jsp";
        } else {
            return "redirect:/admission/api/home?" + request.getQueryString();
        }
    }
}
