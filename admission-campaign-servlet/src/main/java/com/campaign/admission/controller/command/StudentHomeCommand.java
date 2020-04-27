package com.campaign.admission.controller.command;

import com.campaign.admission.service.StudentService;

import javax.servlet.http.HttpServletRequest;

public class StudentHomeCommand implements Command {

    private final StudentService studentService;

    public StudentHomeCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        Boolean enrollment = studentService.checkUserEnrollment(email);
        request.setAttribute("enrollment", enrollment);

        return "/WEB-INF/jsp/student/home.jsp";
    }
}
