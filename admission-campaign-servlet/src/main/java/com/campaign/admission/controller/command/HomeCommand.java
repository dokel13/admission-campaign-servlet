package com.campaign.admission.controller.command;

import com.campaign.admission.domain.Role;
import com.campaign.admission.util.EncryptionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HomeCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");
        if (role == Role.STUDENT) {
            return "redirect:/admission/api/student?" + request.getQueryString();
        } else if (role == Role.ADMIN) {
            return "redirect:/admission/api/admin?" + request.getQueryString();
        } else {
            return "/WEB-INF/jsp/home.jsp";
        }
    }
}
