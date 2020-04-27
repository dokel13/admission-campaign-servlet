package com.campaign.admission.controller.command;

import com.campaign.admission.domain.User;
import com.campaign.admission.exception.ServiceRuntimeException;
import com.campaign.admission.exception.UserValidatorRuntimeException;
import com.campaign.admission.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {

    public UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try {
            User user = userService.login(User.builder()
                    .withEmail(email)
                    .withPassword(password)
                    .build());

            request.getSession().setAttribute("role", user.getRole());
            request.getSession().setAttribute("email", user.getEmail());
            request.getSession().setAttribute("name", user.getName());
            request.getSession().setAttribute("surname", user.getSurname());
        } catch (ServiceRuntimeException | UserValidatorRuntimeException e) {
            request.getSession().setAttribute("exception", e);
        }

        return "redirect:/admission/api/home?" + request.getQueryString();
    }
}
