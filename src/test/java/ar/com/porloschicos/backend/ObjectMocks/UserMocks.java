package ar.com.porloschicos.backend.ObjectMocks;

import ar.com.porloschicos.backend.model.user.UserDao;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

public class UserMocks {

    public static UserDetails getUserDetails() {
        return new org.springframework.security.core.userdetails.User(
                "nicolasandreoli9@gmail.com",
                "asdasd123",
                new ArrayList<>());
    }

    public static UserDao getUserDao() {
        return new UserDao(
                "nicolasandreoli9@gmail.com",
                "asasd123",
                "nicolasandreoli9@gmail.com",
                123123
                );
    }

}
