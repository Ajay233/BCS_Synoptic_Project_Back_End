package quizManager.api.Authentication.model;

import lombok.Data;

@Data
public class AuthRequest {

    private String username;
    private String password;

    public AuthRequest(){}

}
