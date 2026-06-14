package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

@Getter
public class UserLoginResponse {

    private String message;

    private Boolean success;

    private String token;

    public UserLoginResponse(String message, Boolean success,String token) {
        this.message = message;
        this.success = success;
        this.token = token;
    }
}
