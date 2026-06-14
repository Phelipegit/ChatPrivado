package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

@Getter
public class UserUpdatePasswordRequest {
    
    private String atualPassword;

    private String newPassword;

    private String confirmNewPassword;

    public UserUpdatePasswordRequest(String atualPassword, String newPassword, String confirmNewPassword) {
        this.atualPassword = atualPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }
}
