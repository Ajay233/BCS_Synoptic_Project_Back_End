package quizManager.api.UnitTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import quizManager.api.Answer.Answer;
import quizManager.api.Answer.AnswerRepository;
import quizManager.api.Answer.AnswerService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AnswerServiceTest {

    @Autowired
    AnswerService answerService;

    @Autowired
    AnswerRepository answerRepository;


    @BeforeEach
    void setUp(){

    }

    @AfterEach
    void tearDown(){
        answerRepository.truncateTable();
    }

    @Test
    public void capitaliseTest(){
        Answer answer1 = new Answer((long) 1, "A", "test");
        Answer answer2 = new Answer((long) 1, "b", "test");
        Answer answer3 = new Answer((long) 1, "c", "test");
        Answer answer4 = new Answer((long) 1, "D", "test");

        ArrayList<Answer> list = new ArrayList<>();
        list.add(answer1);
        list.add(answer2);
        list.add(answer3);
        list.add(answer4);

        List<Answer> list2 = answerService.capitalise(list);
        Assertions.assertEquals(list2.get(0).getAnswerIndex(), "A");
        Assertions.assertEquals(list2.get(1).getAnswerIndex(), "B");
        Assertions.assertEquals(list2.get(2).getAnswerIndex(), "C");
        Assertions.assertEquals(list2.get(3).getAnswerIndex(), "D");
    }

}
