package quizManager.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import quizManager.api.Answer.Answer;
import quizManager.api.Question.Question;
import quizManager.api.Utils.SortingUtil;

import java.util.ArrayList;

@SpringBootTest
public class SortingUtilTests {

    @Autowired
    SortingUtil sortingUtil;

    private Answer answer1 = new Answer((long) 1, "A", "test answer 1");
    private Answer answer2 = new Answer((long) 1, "B", "test answer 2");
    private Answer answer3 = new Answer((long) 1, "C", "test answer 3");
    private Answer answer4 = new Answer((long) 1, "D", "test answer 4");
    private Answer answer5 = new Answer((long) 1, "E", "test answer 5");

    private Question question1 = new Question((long) 1, 1, "test answer 1");
    private Question question2 = new Question((long) 1, 2, "test answer 2");
    private Question question3 = new Question((long) 1, 3, "test answer 3");
    private Question question4 = new Question((long) 1, 4, "test answer 4");
    private Question question5 = new Question((long) 1, 5, "test answer 5");

    ArrayList<Question> orderedQuestions = new ArrayList<>();
    ArrayList<Answer> orderedAnswers = new ArrayList<>();

    @BeforeEach
    void setUp(){
        orderedQuestions.add(question1);
        orderedQuestions.add(question2);
        orderedQuestions.add(question3);
        orderedQuestions.add(question4);
        orderedQuestions.add(question5);
        orderedAnswers.add(answer1);
        orderedAnswers.add(answer2);
        orderedAnswers.add(answer3);
        orderedAnswers.add(answer4);
        orderedAnswers.add(answer5);
    }

    @Test
    void questionSelectSortTest(){
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(question3);
        questions.add(question5);
        questions.add(question2);
        questions.add(question1);
        questions.add(question4);
        sortingUtil.questionsSelectSort(questions, questions.size());
        Assertions.assertEquals(questions, orderedQuestions);
    }

    @Test
    void answerSelectSortTest(){
        ArrayList<Answer> answers = new ArrayList<>();
        answers.add(answer3);
        answers.add(answer5);
        answers.add(answer2);
        answers.add(answer1);
        answers.add(answer4); 
        sortingUtil.answerSelectSort(answers, answers.size());
        System.out.println(answers);
        Assertions.assertEquals(answers, orderedAnswers);
    }

}
