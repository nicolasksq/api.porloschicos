package ar.com.porloschicos.backend.unitTests;

import ar.com.porloschicos.backend.ObjectMocks.UserMocks;
import ar.com.porloschicos.backend.model.user.UserDao;
import ar.com.porloschicos.backend.repository.UserRepository;
import ar.com.porloschicos.backend.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    void whenLoadUserByUsernameThenGetUserDetails() {
        UserDetails userDetailsMock = UserMocks.getUserDetails();
        UserDao userDao = UserMocks.getUserDao();

        when(userRepository.findByUsername(userDao.getUsername())).thenReturn(userDao);

        Assertions.assertEquals(userDetailsMock, userService.loadUserByUsername(userDao.getUsername()));
    }

    @Test
    void whenLoadUserByUsernameThenGetUserNotFoundException() {
        UserDao userDao = UserMocks.getUserDao();

        when(userRepository.findByUsername(userDao.getUsername())).thenReturn(null);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(userDao.getUsername());
        });
    }

    @Test
    void whenUserIsSaveThenReturnUser() {
        UserDao userDao = UserMocks.getUserDao();

        when(userRepository.save(any())).thenReturn(userDao);

        Assertions.assertEquals(userDao, userService.save(userDao));
    }

    @Test
    void whenUserIsSaveThenReturnDuplicateEntryException() {
        UserDao userDao = UserMocks.getUserDao();

        when(userRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(userDao);
        });
    }
}