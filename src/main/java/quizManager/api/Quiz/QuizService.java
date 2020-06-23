package quizManager.api.Quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quizManager.api.Question.Question;
import quizManager.api.Question.QuestionService;
import quizManager.api.Question.QuestionsRepository;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionsRepository questionsRepository;


    private Boolean nameValid(Quiz quiz){
        return !quiz.getName().isEmpty() && !quiz.getName().matches("\\s+");
    }

    public Boolean quizFieldValid(Quiz quiz){
        return nameValid(quiz);
    }

    public void deleteQuiz(Quiz quiz){
        List<Question> questions = questionsRepository.findByQuizId(quiz.getId());
        questionService.deleteQuestions(questions);
        quizRepository.delete(quiz);
    }

}
