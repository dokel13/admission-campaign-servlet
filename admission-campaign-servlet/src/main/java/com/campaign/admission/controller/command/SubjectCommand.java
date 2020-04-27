package com.campaign.admission.controller.command;

import com.campaign.admission.domain.Exam;
import com.campaign.admission.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;

public class SubjectCommand implements Command {

    private final AdminService adminService;

    public SubjectCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String subject = request.getQueryString().replaceAll("&.*", "");
        int page = parseInt(ofNullable(request.getParameter("page")).orElse("1")) - 1;
        if (page < 0) {
            page = 0;
        }
        List<Exam> exams = adminService.findExamsPaginated(subject, page, 3);
        request.setAttribute("subject", subject);
        request.setAttribute("exams", exams);
        request.setAttribute("page", page + 1);

        return "/WEB-INF/jsp/admin/subject.jsp";
    }
}
