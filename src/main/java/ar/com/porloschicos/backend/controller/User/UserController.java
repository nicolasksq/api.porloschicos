package ar.com.porloschicos.backend.controller.User;

import ar.com.porloschicos.backend.config.JwtTokenUtil;
import ar.com.porloschicos.backend.controller.User.Exceptions.ExceptionAuth;
import ar.com.porloschicos.backend.model.user.UserDao;
import ar.com.porloschicos.backend.model.user.UserDto;
import ar.com.porloschicos.backend.repository.UserRepository;
import ar.com.porloschicos.backend.services.JwtUserDetailsService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class UserController {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/me", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String me(HttpServletRequest request) {
        log.debug(request.toString());
        String[] token = request.getHeader("Authorization").split(" ");
        log.debug(token.toString());

        UserDao user = getUserFromToken(token[1]);
        Gson gson = new Gson();
        String json = gson.toJson(user);

        return json;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody @Validated UserDto user) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userDetailsService.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new ExceptionAuth(e.getMostSpecificCause().getMessage(), e);
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    private UserDao getUserFromToken(String token) {
        return userDao.findByUsername(jwtTokenUtil.getUsernameFromToken(token));
    }
}
