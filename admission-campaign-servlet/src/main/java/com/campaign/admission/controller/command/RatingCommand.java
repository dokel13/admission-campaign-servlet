package com.campaign.admission.controller.command;

import com.campaign.admission.domain.Application;
import com.campaign.admission.service.StudentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.campaign.admission.util.PaginationUtils.countPages;
import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;

public class RatingCommand implements Command {

    private static final Integer PAGE_SIZE = 3;
    private static final String PAGE_STRING = "page=";

    private final StudentService studentService;

    public RatingCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        int page = parseInt(ofNullable(request.getParameter("page")).orElse("1"));
        Application application = studentService.getApplication(email);
        if (application == null) {

            return "redirect:/admission/api/home?" + request.getQueryString().replace(PAGE_STRING + page + "&", "");
        }
        String specialty = application.getSpecialty().getName();
        int pagesCount = countPages(PAGE_SIZE, studentService.countApplicationsBySpecialty(specialty));
        if (page > pagesCount) {

            return "redirect:/admission/api/student/rating?" + request.getQueryString()
                    .replace(PAGE_STRING + page, PAGE_STRING + pagesCount);
        }
        List<Application> applications = studentService.getApplicationsPaginated(specialty, page - 1, PAGE_SIZE);
        request.setAttribute("specialty", specialty);
        request.setAttribute("apps", applications);
        request.setAttribute("page", page);

        return "/WEB-INF/jsp/student/rating.jsp";
    }
}
