package quizManager.api.Answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    private Boolean questionIdValid(List<Answer> answers){
        return answers.stream().allMatch(answer -> answer.getQuestionId().getClass().equals(Long.class));
    }

    private Boolean indexesValid(List<Answer> answers){
        return answers.stream().allMatch(answer ->
                answer.getAnswerIndex().getClass().equals(String.class) && answer.getAnswerIndex().length() == 1
        );
    }

    private Boolean descriptionValid(List<Answer> answers){
        return answers.stream().allMatch(answer ->
                !answer.getDescription().isEmpty() && !answer.getDescription().matches("\\s+")
        );
    }

    public Boolean answerFieldsValid(List<Answer> answers){
        return questionIdValid(answers) && indexesValid(answers) && descriptionValid(answers);
    }

    public Boolean allExist(List<Answer> answers){
        return answers.stream().allMatch(answer -> answerRepository.existsById(answer.getId()));
    }

    public List<Answer> capitalise(List<Answer> answers){
        ArrayList<Answer> list = new ArrayList<>();
        answers.stream().forEach(answer -> {
            if(answer.getAnswerIndex().matches("[a-z]")){
                answer.setAnswerIndex(answer.getAnswerIndex().toUpperCase());
                list.add(answer);
            } else {
                list.add(answer);
            }
        });
        return list;
    }

}
