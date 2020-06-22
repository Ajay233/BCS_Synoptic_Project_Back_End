package quizManager.api.Quiz;

import lombok.Data;

@Data
public class Quiz {

    private Long id;
    private String name;

    public Quiz(){}
    public Quiz(String name){
        this.name = name;
    }

}

