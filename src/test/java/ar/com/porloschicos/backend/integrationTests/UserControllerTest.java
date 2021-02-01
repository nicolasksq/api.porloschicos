package ar.com.porloschicos.backend.integrationTests;

import ar.com.porloschicos.backend.ObjectMocks.JwtMocks;
import ar.com.porloschicos.backend.ObjectMocks.UserMocks;
import ar.com.porloschicos.backend.model.user.UserDao;
import ar.com.porloschicos.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static ar.com.porloschicos.backend.utils.Util.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest extends IntegrationTest {

    @MockBean
    private UserRepository userRepository;

    @Test
    void registerTest() throws Exception {
        UserDao userDao = UserMocks.getUserDao();

        when(userRepository.save(any())).thenReturn(userDao);

        mockMvc.perform(post("/register")
                .content(asJsonString(userDao))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.username").value(userDao.getUsername()))
                .andExpect(jsonPath("$.email").value(userDao.getEmail()))
                .andExpect(jsonPath("$.du").value(userDao.getDu()))
                .andReturn();

        verify(userRepository, times(1)).save(any());

    }

    @Test
    void failRegisterForDuplicateEntryTest() throws Exception {
        UserDao userDao = UserMocks.getUserDao();

        when(userRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/register")
                .content(asJsonString(userDao))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.data").hasJsonPath())
                .andReturn();

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void meTest() throws Exception {
        UserDao userDao = UserMocks.getUserDao();

        when(userRepository.findByUsername(any())).thenReturn(userDao);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + JwtMocks.getToken());

        mockMvc.perform(get("/me")
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").hasJsonPath())
                .andExpect(jsonPath("$.username").value(userDao.getUsername()))
                .andExpect(jsonPath("$.email").value(userDao.getEmail()))
                .andExpect(jsonPath("$.du").value(userDao.getDu()))
                .andExpect(jsonPath("$.created_at").hasJsonPath())
                .andReturn();

        verify(userRepository, times(2)).findByUsername(any());
    }

    @Test
    void failMeTest() throws Exception {

        HttpHeaders httpHeaders = new HttpHeaders();

        mockMvc.perform(get("/me")
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();

        verify(userRepository, times(0)).findByUsername(any());

    }

}