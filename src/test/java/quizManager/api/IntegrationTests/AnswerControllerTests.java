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
import quizManager.api.Answer.Answer;
import quizManager.api.Answer.AnswerRepository;
import quizManager.api.Authentication.model.UserPrincipal;
import quizManager.api.JwtUtil;
import quizManager.api.User.model.User;
import quizManager.api.User.repository.UserRepository;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
public class AnswerControllerTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MockMvc mockMvc;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private User user = new User("testUser", encoder.encode("testpassword"), "Edit");
    private Answer answer1;
    private Answer answer2;
    private Answer answer3;
    private Answer answer4;
    private HttpHeaders header = new HttpHeaders();
    private Gson gson = new Gson();


    @BeforeEach
    void setUp(){
        User savedUser = userRepository.save(user);
        UserDetails userDetails = new UserPrincipal(savedUser);
        String jwt = jwtUtil.generateJwt(userDetails);
        header.add("Content-Type", "application/json");
        header.add("Authorization", "Bearer " + jwt);

        answer1 = answerRepository.save(new Answer((long) 1, "A", "test answer"));
        answer2 = answerRepository.save(new Answer((long) 1, "B", "test answer"));
        answer3 = answerRepository.save(new Answer((long) 2, "A", "test answer"));
        answer4 = answerRepository.save(new Answer((long) 2, "B", "test answer"));

    }

    @AfterEach
    void tearDown(){
        userRepository.truncateTable();
        answerRepository.truncateTable();
    }


    @Test
    public void findAnswerByQuestionId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/answer/findByQuestionId")
                .headers(header)
                .param("questionId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").value(answer1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1]").value(answer2));
    }

    @Test
    public void createAnswer() throws Exception {

        ArrayList<Answer> answers = new ArrayList<>();
        Answer answer5 = new Answer((long) 2, "C", "test answer");
        Answer answer6 = new Answer((long) 2, "D", "test answer");

        answers.add(answer5);
        answers.add(answer6);

        mockMvc.perform(MockMvcRequestBuilders.post("/answer/create")
                .headers(header)
                .content(gson.toJson(answers)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").value(answerRepository.findById((long) 5).get()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1]").value(answerRepository.findById((long) 6).get()));
    }

    @Test
    public void updateAnswer() throws Exception {

        ArrayList<Answer> answers = new ArrayList<>();
        answer1.setDescription("Updated question 1");
        answer2.setDescription("Updated question 2");

        answers.add(answer1);
        answers.add(answer2);

        mockMvc.perform(MockMvcRequestBuilders.put("/answer/update")
                .headers(header)
                .content(gson.toJson(answers)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").value(answer1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1]").value(answer2));
    }

    @Test
    public void deleteAnswer() throws Exception {

        ArrayList<Answer> answers = new ArrayList<>();
        answer1.setDescription("Updated question 1");
        answer2.setDescription("Updated question 2");

        answers.add(answer1);
        answers.add(answer2);

        mockMvc.perform(MockMvcRequestBuilders.delete("/answer/delete")
                .headers(header)
                .content(gson.toJson(answers)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Answer deleted"));
    }

}
