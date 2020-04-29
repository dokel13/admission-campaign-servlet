package com.campaign.admission.controller.command;

import javax.servlet.http.HttpServletRequest;

public class ErrorCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/jsp/error.jsp";
    }
}
