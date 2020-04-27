package com.campaign.admission.controller.command;

import com.campaign.admission.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AdminHomeCommand implements Command {

    private final AdminService adminService;

    public AdminHomeCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<String> subjects = adminService.findAllSubjects();
        if (subjects != null) {
            request.setAttribute("subjects", subjects);
        }

        return "/WEB-INF/jsp/admin/home.jsp";
    }
}
