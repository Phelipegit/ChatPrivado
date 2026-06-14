package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

@Getter
public class UserRegisterResponse {

    private boolean success;

    private String message;

    public UserRegisterResponse(boolean success,String message) {
        this.success = success;
        this.message = message;
    }
}
