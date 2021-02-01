package ar.com.porloschicos.backend.controller.Authentication;

import ar.com.porloschicos.backend.config.JwtTokenUtil;
import ar.com.porloschicos.backend.model.*;
import ar.com.porloschicos.backend.model.auth.JwtRequest;
import ar.com.porloschicos.backend.model.auth.JwtResponse;
import ar.com.porloschicos.backend.model.TokenRequest;
import ar.com.porloschicos.backend.services.JwtService;
import ar.com.porloschicos.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) {
        ResponseEntity<?> response;
        try {
            jwtService.validate(jwtRequest);
            UserDetails user =
                    userService.loadUserByUsername(jwtRequest.getUsername());
            response = ResponseEntity.ok(new JwtResponse("OK", jwtTokenUtil.generateToken(user)));
        }catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new JwtResponse("FAIL", null, e.getMessage()));
        }

        return response;
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public ResponseEntity<?> validateJwt(@RequestParam TokenRequest token) {
        return ResponseEntity.ok(
                new Response("OK", jwtTokenUtil.canTokenBeRefreshed(token.getToken()).toString()));
    }
}
