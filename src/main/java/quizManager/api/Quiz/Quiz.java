package quizManager.api.Quiz;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Quiz(){}
    public Quiz(String name){
        this.name = name;
    }

}

