package ar.com.porloschicos.backend.integrationTests;

import ar.com.porloschicos.backend.BackendApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BackendApplication.class)
@AutoConfigureMockMvc
@DirtiesContext
public abstract class IntegrationTest {
    @Autowired
    protected MockMvc mockMvc;
}
