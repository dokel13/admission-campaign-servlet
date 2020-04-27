package com.campaign.admission.controller.command;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();

        return "redirect:/admission/api/home";
    }
}
