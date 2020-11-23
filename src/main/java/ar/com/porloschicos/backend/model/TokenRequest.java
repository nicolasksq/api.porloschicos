package ar.com.porloschicos.backend.model;

import java.io.Serializable;

public class TokenRequest implements Serializable {

    private String token;

    public TokenRequest() {
    }

    public TokenRequest(String token) {
        this.setToken(token);
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
