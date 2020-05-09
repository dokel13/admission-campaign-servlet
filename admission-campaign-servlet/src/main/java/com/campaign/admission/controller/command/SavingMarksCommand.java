package com.campaign.admission.controller.command;

import com.campaign.admission.service.AdminService;

import javax.servlet.http.HttpServletRequest;

public class SavingMarksCommand implements Command {

    private final AdminService adminService;

    public SavingMarksCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String subject = request.getQueryString().replaceAll("&.*", "");
        adminService.saveMarks(subject, request.getParameterValues("email"), request.getParameterValues("mark"));

        return "redirect:/admission/api/admin/subject?" + request.getQueryString();
    }
}
