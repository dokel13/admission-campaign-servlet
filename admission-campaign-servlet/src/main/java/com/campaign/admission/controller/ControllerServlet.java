package com.campaign.admission.controller;

import com.campaign.admission.controller.command.*;
import com.campaign.admission.dao.*;
import com.campaign.admission.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerServlet extends HttpServlet {

    private static final Map<String, Command> COMMANDS = new HashMap<>();

    @Override
    public void init() throws ServletException {
        UserDao userDao = new UserDaoImpl();
        ExamDao examDao = new ExamDaoImpl();
        ApplicationDao applicationDao = new ApplicationDaoImpl();
        SpecialtyDao specialtyDao = new SpecialtyDaoImpl();
        UserService userService = new UserServiceImpl(userDao);
        StudentService studentService = new StudentServiceImpl(examDao, specialtyDao, applicationDao);
        AdminService adminService = new AdminServiceImpl(examDao, specialtyDao, applicationDao);

        COMMANDS.put("home", new HomeCommand());
        COMMANDS.put("register", new RegistrationCommand(userService));
        COMMANDS.put("login", new LoginCommand(userService));
        COMMANDS.put("logout", new LogoutCommand());
        COMMANDS.put("error", new ErrorCommand());
        COMMANDS.put("student", new StudentHomeCommand(studentService));
        COMMANDS.put("student/subjects", new SubjectsCommand(studentService));
        COMMANDS.put("student/exams", new ExamRegistrationCommand(studentService));
        COMMANDS.put("student/specialties", new SpecialtiesCommand(studentService));
        COMMANDS.put("student/specialty", new SpecialtyCommand(studentService));
        COMMANDS.put("student/specialty/apply", new SpecialtyApplyCommand(studentService));
        COMMANDS.put("student/rating", new RatingCommand(studentService));
        COMMANDS.put("student/results", new ResultCommand(studentService));
        COMMANDS.put("admin", new AdminHomeCommand(adminService));
        COMMANDS.put("admin/set_admission", new AdmissionCommand(adminService));
        COMMANDS.put("admin/subject", new SubjectCommand(adminService));
        COMMANDS.put("admin/subject/save_marks", new SavingMarksCommand(adminService));
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI().replaceAll(".*admission/api/", "");
        String page = COMMANDS.get(path).execute(request);

        if (page.contains("redirect:")) {
            response.sendRedirect(page.replace("redirect:", ""));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
