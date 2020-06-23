package quizManager.api.Question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quizManager.api.Answer.Answer;
import quizManager.api.Answer.AnswerRepository;
import quizManager.api.Answer.AnswerService;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionsRepository questionsRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    AnswerService answerService;

    private Boolean quizIdValid(List<Question> questions){
        return questions.stream().allMatch(question -> question.getQuizId().getClass().equals(Long.class));
    }

    private Boolean questionNumberValid(List<Question> questions){
        return questions.stream().allMatch(question -> question.getQuestionNumber() > 0);
    }

    private Boolean descriptionValid(List<Question> questions){
        return questions.stream().allMatch(question ->
                !question.getDescription().isEmpty() && !question.getDescription().matches("\\s+")
        );
    }

    public Boolean questionFieldsValid(List<Question> questions){
        return quizIdValid(questions) && questionNumberValid(questions) && descriptionValid(questions);
    }

    public Boolean allExist(List<Question> questions){
        return questions.stream().allMatch(question -> questionsRepository.existsById(question.getId()));
    }

    public void deleteQuestions(List<Question> questions){
        questions.stream().forEach(question -> {
            List<Answer> answers = answerRepository.findByQuestionId(question.getId());
            if(!answers.isEmpty()){
                answerRepository.deleteAll(answers);
            } else {
                return;
            }
        });
        questionsRepository.deleteAll(questions);
    }

}
