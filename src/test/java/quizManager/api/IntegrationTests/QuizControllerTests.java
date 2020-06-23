package quizManager.api.IntegrationTests;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import quizManager.api.Authentication.model.UserPrincipal;
import quizManager.api.JwtUtil;
import quizManager.api.Quiz.Quiz;
import quizManager.api.Quiz.QuizRepository;
import quizManager.api.User.model.User;
import quizManager.api.User.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class QuizControllerTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MockMvc mockMvc;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private User user = new User("testUser", encoder.encode("testpassword"), "Edit");
    private Quiz quiz1 = new Quiz("testQuiz");
    private Quiz savedQuiz;
    private HttpHeaders header = new HttpHeaders();
    private Gson gson = new Gson();


    @BeforeEach
    void setUp(){
        User savedUser = userRepository.save(user);
        UserDetails userDetails = new UserPrincipal(savedUser);
        String jwt = jwtUtil.generateJwt(userDetails);
        header.add("Content-Type", "application/json");
        header.add("Authorization", "Bearer " + jwt);
        savedQuiz = quizRepository.save(quiz1);
    }

    @AfterEach
    void tearDown(){
        userRepository.truncateTable();
        quizRepository.truncateTable();
    }

    @Test
    public void findQuizByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/quiz/findByName")
                .headers(header)
                .param("name", "testQuiz"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").value(savedQuiz));
    }

    @Test
    public void createQuiz() throws Exception {

        Quiz quizToCreate = new Quiz("newQuiz");

        mockMvc.perform(MockMvcRequestBuilders.post("/quiz/create")
                .headers(header)
                .content(gson.toJson(quizToCreate)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(quizRepository.findById((long) 2).get()));
    }

    @Test
    public void updateQuiz() throws Exception {

        savedQuiz.setName("updatedQuizName");

        mockMvc.perform(MockMvcRequestBuilders.put("/quiz/update")
                .headers(header)
                .content(gson.toJson(savedQuiz)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("updatedQuizName"));
    }

    @Test
    public void deleteQuiz() throws Exception {

        System.out.println("Quiz exists? " + quizRepository.existsById(savedQuiz.getId()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/quiz/delete")
                .headers(header)
                .content(gson.toJson(savedQuiz)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Quiz deleted"));
    }

}
