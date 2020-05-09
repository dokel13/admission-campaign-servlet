package com.campaign.admission.controller.command;

import com.campaign.admission.service.AdminService;

import javax.servlet.http.HttpServletRequest;

import static java.lang.Boolean.valueOf;

public class AdmissionCommand implements Command {

    private final AdminService adminService;

    public AdmissionCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Boolean open = null;
        if (request.getParameter("open") != null) {
            open = valueOf(request.getParameter("open"));
            adminService.setAdmission(open);
        }
        open = adminService.checkAdmission();

        return "redirect:/admission/api/admin?" + request.getQueryString().replace("open=" + open + "&", "");
    }
}
