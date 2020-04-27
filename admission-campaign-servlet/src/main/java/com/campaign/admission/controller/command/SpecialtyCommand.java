package com.campaign.admission.controller.command;

import com.campaign.admission.domain.Specialty;
import com.campaign.admission.service.StudentService;

import javax.servlet.http.HttpServletRequest;

public class SpecialtyCommand implements Command {

    private final StudentService studentService;

    public SpecialtyCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String specialtyName = request.getQueryString().replaceAll("&.*", "");
        Specialty specialty = studentService.findSpecialty(specialtyName);
        request.setAttribute("specialty", specialty);

        return "/WEB-INF/jsp/student/specialty.jsp";
    }
}
