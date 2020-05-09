package com.campaign.admission.service;

import com.campaign.admission.dao.UserDao;
import com.campaign.admission.domain.Role;
import com.campaign.admission.domain.User;
import com.campaign.admission.exception.ServiceRuntimeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.campaign.admission.domain.User.builder;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Rule
    public ExpectedException expectedException = none();

    private final User enteringUser = builder()
            .withEmail("email@mail.ua")
            .withPassword("password")
            .withRole(Role.STUDENT)
            .build();

    private final User existingUser = builder()
            .withEmail("email@mail.ua")
            .withPassword("_M�;Z�e�\u001D�'\u07B8�ϙ")
            .withRole(Role.STUDENT)
            .build();

    @Test
    public void loginShouldReturnUser() {
        when(userDao.findByEmail(enteringUser.getEmail())).thenReturn(of(existingUser));
        assertThat(userService.login(enteringUser), is(existingUser));

        verify(userDao).findByEmail(enteringUser.getEmail());
    }

    @Test
    public void loginShouldThrowException() {
        expectedException.expect(ServiceRuntimeException.class);
        expectedException.expectMessage("Login exception! User doesn`t exist!");
        try {
            userService.login(enteringUser);
        } finally {
            verify(userDao).findByEmail(enteringUser.getEmail());
        }
    }

    @Test
    public void registerShouldReturnUser() {
        when(userDao.save(existingUser)).thenReturn(existingUser);
        assertThat(userService.register(enteringUser), is(existingUser));

        verify(userDao).findByEmail(enteringUser.getEmail());
        verify(userDao).save(enteringUser);
    }

    @Test
    public void registerShouldThrowException() {
        expectedException.expect(ServiceRuntimeException.class);
        expectedException.expectMessage("Registration exception! User already exists!");
        when(userDao.findByEmail(enteringUser.getEmail())).thenReturn(of(existingUser));
        try {
            userService.register(enteringUser);
        } finally {
            verify(userDao).findByEmail(enteringUser.getEmail());
        }
    }
}
