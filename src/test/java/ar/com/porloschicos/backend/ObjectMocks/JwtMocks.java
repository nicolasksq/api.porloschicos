package ar.com.porloschicos.backend.ObjectMocks;

import ar.com.porloschicos.backend.model.auth.JwtRequest;

public class JwtMocks {

    public static JwtRequest getJwtRequest() {
        return new JwtRequest("nicolasandreoli9@gmail.com", "asdasd");
    }

    public static JwtRequest getInvalidJwtRequest() {
        return new JwtRequest();
    }

    public static String getToken() {
        return "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuaWNvbGFzYW5kcmVvbGk5QGdtYWlsLmNvbSIsImV4cCI6NjUxMjMwNzMyMCwiaWF0IjoxNjExODU2MzI1fQ.cQ92gCx6n40T3O3Tef6WzfOqzpQ6F7Q9Ngc41NDWLPUWKJJAkWHOqFsSD9YueT2lRPr0DsdFQVF-ssw94-1eCw";
    }
}
