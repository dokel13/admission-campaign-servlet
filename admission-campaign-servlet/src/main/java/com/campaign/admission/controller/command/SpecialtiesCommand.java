package com.campaign.admission.controller.command;

import com.campaign.admission.service.StudentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SpecialtiesCommand implements Command {

    private final StudentService studentService;

    public SpecialtiesCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<String> specialties = studentService.getAllSpecialties();
        request.setAttribute("specialties", specialties);

        return "/WEB-INF/jsp/student/specialties.jsp";
    }
}
