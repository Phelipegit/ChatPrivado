package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

@Getter
public class UserUpdatePasswordResponse {

    private String message;

    private Boolean success;

    public UserUpdatePasswordResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
}
