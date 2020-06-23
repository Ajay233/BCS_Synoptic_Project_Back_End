package quizManager.api.IntegrationTests;

import com.google.gson.Gson;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import quizManager.api.Authentication.model.AuthRequest;
import quizManager.api.JwtUtil;
import quizManager.api.User.model.User;
import quizManager.api.User.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MockMvc mockMvc;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private User user = new User("testUser", encoder.encode("testpassword"), "Edit");
    User savedUser;
    private HttpHeaders header = new HttpHeaders();
    private Gson gson = new Gson();


    @BeforeEach
    void setUp(){
        savedUser = userRepository.save(user);
        header.add("Content-Type", "application/json");
    }

    @AfterEach
    void tearDown(){
        userRepository.truncateTable();
    }

    @Test
    public void loginTest() throws Exception{

        String regex = "^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$";

        savedUser.setPassword("");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .headers(header)
                .content(gson.toJson(new AuthRequest("testUser", "testpassword"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user").value(savedUser))
                .andExpect(MockMvcResultMatchers.jsonPath("$.jwt", MatchesPattern.matchesPattern(regex)));
    }

}
