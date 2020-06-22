package quizManager.api.Authentication.model;

import lombok.Data;
import quizManager.api.User.model.User;

@Data
public class AuthResponse {

    private User user;
    private String jwt;

    public AuthResponse (){}

    public AuthResponse(User user, String jwt){
        this.user = user;
        this.jwt = jwt;
    }

}
