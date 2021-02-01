package ar.com.porloschicos.backend.model.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class JwtResponse {

    private final String status;

    @JsonProperty("token")
    private final String jwtToken;
    private String error;

    public JwtResponse(String status, String jwtToken) {
        this.jwtToken   = jwtToken;
        this.status     = status;
    }

    public JwtResponse(String status, String jwtToken, String error) {
        this.status     = status;
        this.jwtToken   = jwtToken;
        this.error      = error;
    }
}
