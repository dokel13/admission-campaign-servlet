package com.campaign.admission.controller.command;

import com.campaign.admission.exception.AdmissionValidatorRuntimeException;
import com.campaign.admission.exception.ServiceRuntimeException;
import com.campaign.admission.service.StudentService;

import javax.servlet.http.HttpServletRequest;

public class SpecialtyApplyCommand implements Command {

    private final StudentService studentService;

    public SpecialtyApplyCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        String specialty = request.getQueryString().replaceAll("&.*", "");
        try {
            studentService.specialtyApply(email, specialty);
        } catch (ServiceRuntimeException | AdmissionValidatorRuntimeException e) {
            request.getSession().setAttribute("exception", e);

            return "redirect:/admission/api/student/specialty?" + request.getQueryString();
        }

        return "redirect:/admission/api/home?" + request.getQueryString();
    }
}
