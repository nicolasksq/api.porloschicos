package ar.com.porloschicos.backend.controller.User;

import ar.com.porloschicos.backend.config.JwtTokenUtil;
import ar.com.porloschicos.backend.model.Response;
import ar.com.porloschicos.backend.model.user.UserDao;
import ar.com.porloschicos.backend.repository.UserRepository;
import ar.com.porloschicos.backend.services.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class UserController {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/me", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> me(HttpServletRequest request) {
        ResponseEntity<?> response;
        String[] token = request.getHeader("Authorization").split(" ");

        try {
            UserDao user = userService.findByUsername(jwtTokenUtil.getUsernameFromToken(token[1]));
            response = ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response("FAIL", "TRY TO GET NEW TOKEN"));
        }

        return response;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody @Validated UserDao user) {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
        } catch (DataIntegrityViolationException e) {
            response =  ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new Response("FAIL", Objects.requireNonNull(e.getRootCause()).getMessage()));
        } catch (Exception e) {
            throw new NullPointerException();
        }

        return response;
    }

}
