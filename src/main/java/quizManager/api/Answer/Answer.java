package quizManager.api.Answer;


import lombok.Data;

@Data
public class Answer {

    private Long id;
    private String answerIndex;
    private String description;

    public Answer(){}
    public Answer(String answerIndex, String description){
        this.answerIndex = answerIndex;
        this.description = description;
    }

}
