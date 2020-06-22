package quizManager.api.Question;

import lombok.Data;

@Data
public class Question {

    private Long id;
    private int questionNumber;
    private String description;

    public Question(){}
    public Question(int questionNumber, String description){
        this.questionNumber = questionNumber;
        this.description = description;
    }

}
