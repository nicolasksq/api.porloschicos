package ar.com.porloschicos.backend.controller.Authentication;

import ar.com.porloschicos.backend.config.JwtTokenUtil;
import ar.com.porloschicos.backend.controller.Authentication.Exceptions.ExceptionAuthInvalidSignature;
import ar.com.porloschicos.backend.model.*;
import ar.com.porloschicos.backend.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse("OK", token));
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public ResponseEntity<?> validateJwt(@RequestParam TokenRequest token) throws Exception {
        System.out.println("AAAAAASDASDADASDASDASASDSDADASDDASDASDASD       " + token.getToken());

        try {
            Boolean valid = jwtTokenUtil.canTokenBeRefreshed(token.getToken());
            return ResponseEntity.ok(new Response("OK", valid.toString()));
        } catch (Exception e) {
            throw new ExceptionAuthInvalidSignature(e.getMessage(), e);
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
