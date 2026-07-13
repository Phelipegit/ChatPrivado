package ProjetoPhelipe.ChatPrivado.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class UserUpdatePasswordRequest {

    @Email
    private String email;

    private String token;

    private String atualPassword;

    private String newPassword;

    private String confirmNewPassword;

    public UserUpdatePasswordRequest(String email,String id,String atualPassword, String newPassword, String confirmNewPassword) {
        this.email = email;
        this.token = id;
        this.atualPassword = atualPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }
}
