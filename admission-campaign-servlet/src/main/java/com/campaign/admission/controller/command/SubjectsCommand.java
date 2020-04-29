package com.campaign.admission.controller.command;

import com.campaign.admission.exception.ServiceRuntimeException;
import com.campaign.admission.service.StudentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SubjectsCommand implements Command {

    private final StudentService studentService;

    public SubjectsCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        try {
            List<String> subjects = studentService.getUserFreeSubjects(email);
            request.setAttribute("subjects", subjects);
        } catch (ServiceRuntimeException e) {
            request.setAttribute("exception", e);
        }

        return "/WEB-INF/jsp/student/subjects.jsp";
    }
}
