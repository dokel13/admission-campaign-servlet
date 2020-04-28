package com.campaign.admission.controller.command;

import com.campaign.admission.service.StudentService;

import javax.servlet.http.HttpServletRequest;

public class ExamRegistrationCommand implements Command {

    private final StudentService studentService;

    public ExamRegistrationCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        studentService.saveExamSubjects(request.getParameterValues("subject"), email);

        return "redirect:/admission/api/home?" + request.getQueryString();
    }
}
