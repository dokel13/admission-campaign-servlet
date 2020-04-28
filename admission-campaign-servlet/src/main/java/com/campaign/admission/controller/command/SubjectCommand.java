package com.campaign.admission.controller.command;

import com.campaign.admission.domain.Exam;
import com.campaign.admission.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;

public class SubjectCommand implements Command {

    private static final Integer PAGE_SIZE = 3;
    private static final String PAGE_STRING = "page=";

    private final AdminService adminService;

    public SubjectCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String subject = request.getQueryString().replaceAll("&.*", "");
        int page = parseInt(ofNullable(request.getParameter("page")).orElse("1"));
        int examsCount = adminService.countExamsBySubject(subject);
        int pagesCount;
        if (examsCount >= 0 && examsCount <= PAGE_SIZE) {
            pagesCount = 1;
        } else {
            pagesCount = examsCount / PAGE_SIZE;
            if (examsCount > 2 && (examsCount % PAGE_SIZE) > 0) {
                pagesCount += 1;
            }
        }
        if (page > pagesCount) {

            return "redirect:/admission/api/admin/subject?" + request.getQueryString()
                    .replace(PAGE_STRING + page, PAGE_STRING + pagesCount);
        }
        List<Exam> exams = adminService.findExamsPaginated(subject, page - 1, PAGE_SIZE);
        request.setAttribute("subject", subject);
        request.setAttribute("exams", exams);
        request.setAttribute("page", page);

        return "/WEB-INF/jsp/admin/subject.jsp";
    }
}
