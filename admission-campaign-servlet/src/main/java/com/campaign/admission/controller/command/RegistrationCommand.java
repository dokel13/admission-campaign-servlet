package com.campaign.admission.controller.command;

import com.campaign.admission.domain.Role;
import com.campaign.admission.domain.User;
import com.campaign.admission.exception.ServiceRuntimeException;
import com.campaign.admission.exception.UserValidatorRuntimeException;
import com.campaign.admission.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {

    private final UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String password = request.getParameter("password");
        try {
            User user = userService.register(User.builder()
                    .withRole(Role.STUDENT)
                    .withEmail(email)
                    .withName(name)
                    .withSurname(surname)
                    .withPassword(password)
                    .build());

            request.getSession().setAttribute("role", user.getRole());
            request.getSession().setAttribute("email", user.getEmail());
            request.getSession().setAttribute("name", user.getName());
            request.getSession().setAttribute("surname", user.getSurname());
        } catch (UserValidatorRuntimeException | ServiceRuntimeException e) {
            request.getSession().setAttribute("exception", e);
        }

        return "redirect:/admission/api/home?" + request.getQueryString();
    }
}
