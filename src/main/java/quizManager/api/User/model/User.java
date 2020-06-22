package quizManager.api.User.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String permission;

    public User(){}
    public User(String username, String password, String permission){
        this.username = username;
        this.password = password;
        this.permission = permission;
    }

}
