package ar.com.porloschicos.backend.services;

import ar.com.porloschicos.backend.model.user.UserDao;
import ar.com.porloschicos.backend.model.user.UserDto;
import ar.com.porloschicos.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDao user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    public UserDao save(UserDto user) {
        UserDao newUser = new UserDao(
                user.getUsername(),
                bcryptEncoder.encode(user.getPassword()),
                user.getEmail(),
                user.getDu()
        );

        return userDao.save(newUser);
    }
}
