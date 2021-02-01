package ar.com.porloschicos.backend.services;

import ar.com.porloschicos.backend.model.user.UserDao;
import ar.com.porloschicos.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDao user = userRepository.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException("User not found with username: " + username);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    /**
     * @param username
     * @return UserDao
     */
    public UserDao findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * @param user
     * @throws Exception
     */
    public UserDao save(UserDao user) {
        try {
           return userRepository.save(user);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_USER_DATA", e);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("USER_ALREADY_EXIST", e);
        }
    }

}
