package com.campaign.admission.controller.command;

import com.campaign.admission.domain.Exam;
import com.campaign.admission.service.StudentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ResultCommand implements Command {

    private final StudentService studentService;

    public ResultCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = (String)request.getSession().getAttribute("email");
        List<Exam> exams = studentService.getResults(email);
        request.setAttribute("exams", exams);

        return "/WEB-INF/jsp/student/results.jsp";
    }
}
