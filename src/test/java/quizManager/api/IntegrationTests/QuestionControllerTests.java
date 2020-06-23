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
import quizManager.api.Question.Question;
import quizManager.api.Question.QuestionsRepository;
import quizManager.api.User.model.User;
import quizManager.api.User.repository.UserRepository;

import java.util.ArrayList;


@SpringBootTest
@AutoConfigureMockMvc
public class QuestionControllerTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionsRepository questionsRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MockMvc mockMvc;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private User user = new User("testUser", encoder.encode("testpassword"), "Edit");
    private Question question1 = new Question((long) 1, 1, "test question");
    private Question question2 = new Question((long) 1, 2, "test question2");
    private Question question3 = new Question((long) 2, 1, "test question");
    private Question question4 = new Question((long) 2, 2, "test question2");
    private HttpHeaders header = new HttpHeaders();
    private Gson gson = new Gson();


    @BeforeEach
    void setUp(){
        User savedUser = userRepository.save(user);
        UserDetails userDetails = new UserPrincipal(savedUser);
        String jwt = jwtUtil.generateJwt(userDetails);
        header.add("Content-Type", "application/json");
        header.add("Authorization", "Bearer " + jwt);

        question1 = questionsRepository.save(new Question((long) 1, 1, "test question"));
        question2 = questionsRepository.save(new Question((long) 1, 2, "test question2"));
        question3 = questionsRepository.save(new Question((long) 2, 1, "test question"));
        question4 = questionsRepository.save(new Question((long) 2, 2, "test question2"));
    }

    @AfterEach
    void tearDown(){
        userRepository.truncateTable();
        questionsRepository.truncateTable();
    }


    @Test
    public void findQuestionsByQuizId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/question/findByQuizId")
                .headers(header)
                .param("quizId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").value(question1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1]").value(question2));
    }

    @Test
    public void createQuiz() throws Exception {

        ArrayList<Question> questions = new ArrayList<>();
        Question question5 = new Question((long) 2, 3, "test question");
        Question question6 = new Question((long) 2, 4, "test question");

        questions.add(question5);
        questions.add(question6);

        mockMvc.perform(MockMvcRequestBuilders.post("/question/create")
                .headers(header)
                .content(gson.toJson(questions)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").value(questionsRepository.findById((long) 5).get()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1]").value(questionsRepository.findById((long) 6).get()));
    }

    @Test
    public void updateQuiz() throws Exception {

        ArrayList<Question> questions = new ArrayList<>();
        question1.setDescription("Updated question 1");
        question2.setDescription("Updated question 2");

        questions.add(question1);
        questions.add(question2);

        mockMvc.perform(MockMvcRequestBuilders.put("/question/update")
                .headers(header)
                .content(gson.toJson(questions)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").value(question1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1]").value(question2));
    }

    @Test
    public void deleteQuiz() throws Exception {

        ArrayList<Question> questions = new ArrayList<>();

        questions.add(question1);
        questions.add(question2);

        mockMvc.perform(MockMvcRequestBuilders.delete("/question/delete")
                .headers(header)
                .content(gson.toJson(questions)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Question deleted"));
    }

}
