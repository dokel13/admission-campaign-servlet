package com.campaign.admission.controller.command;

import com.campaign.admission.domain.Exam;
import com.campaign.admission.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.campaign.admission.util.PaginationUtils.countPages;
import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;

public class SubjectCommand implements Command {

    private static final Integer PAGE_SIZE = 3;
    private static final String PAGE_STRING = "page=";
    private static final String QUERY_STRING = "&.*";

    private final AdminService adminService;

    public SubjectCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String subject = request.getQueryString().replaceAll(QUERY_STRING, "");
        int page = parseInt(ofNullable(request.getParameter("page")).orElse("1"));
        int pagesCount = countPages(PAGE_SIZE, adminService.countBySubjectAndApplicationIsNull(subject));
        if (page > pagesCount) {

            return "redirect:/admission/api/admin/subject?" + request.getQueryString()
                    .replace(PAGE_STRING + page, PAGE_STRING + pagesCount);
        }
        List<Exam> exams = adminService.getExamsPaginated(subject, page - 1, PAGE_SIZE);
        request.setAttribute("subject", subject);
        request.setAttribute("exams", exams);
        request.setAttribute("page", page);

        return "/WEB-INF/jsp/admin/subject.jsp";
    }
}
