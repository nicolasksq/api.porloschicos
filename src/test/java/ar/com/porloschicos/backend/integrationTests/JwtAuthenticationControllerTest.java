package ar.com.porloschicos.backend.integrationTests;

import ar.com.porloschicos.backend.ObjectMocks.JwtMocks;
import ar.com.porloschicos.backend.ObjectMocks.UserMocks;
import ar.com.porloschicos.backend.config.JwtTokenUtil;
import ar.com.porloschicos.backend.model.auth.JwtRequest;
import ar.com.porloschicos.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MvcResult;

import static ar.com.porloschicos.backend.utils.Util.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


class JwtAuthenticationControllerTest extends IntegrationTest{

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    void createAuthenticationTokenTest() throws Exception {
        JwtRequest jwtRequest   = JwtMocks.getJwtRequest();
        String tokenExpected    = JwtMocks.getToken();

        when(userRepository.findByUsername(jwtRequest.getUsername())).thenReturn(UserMocks.getUserDao());
        when(jwtTokenUtil.generateToken(any())).thenReturn(tokenExpected);

        MvcResult mvcResult = mockMvc.perform(post("/authenticate")
                .content(asJsonString(jwtRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.token").value(tokenExpected))
                .andReturn();

        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(1)).findByUsername(any());
    }

    @Test
    void failCreateAuthenticationTokenWhenInvalidUserSentTest() throws Exception {
        JwtRequest jwtRequest   = JwtMocks.getInvalidJwtRequest();

        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

        MvcResult mvcResult = mockMvc.perform(post("/authenticate")
                .content(asJsonString(jwtRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.error").value("INVALID_CREDENTIALS"))
                .andReturn();

        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    void validateJwtTest() throws Exception {

        String token = JwtMocks.getToken();

        MvcResult mvcResult = mockMvc.perform(get("/validate")
                .param("token", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andReturn();

        verify(jwtTokenUtil, times(1)).canTokenBeRefreshed(any());
    }
}