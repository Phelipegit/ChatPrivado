package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

@Getter
public class UserRegisterRequest {

    private String email;

    private String username;

    private String password;

    public UserRegisterRequest(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.username = username;
    }
}
