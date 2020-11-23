package ar.com.porloschicos.backend.model.auth;

import ar.com.porloschicos.backend.model.Response;

public class JwtResponse extends Response {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwtToken;

    public JwtResponse(String status, String jwtToken) {
        super(status);
        this.jwtToken = jwtToken;
    }

    public String getToken() {
        return this.jwtToken;
    }
}
