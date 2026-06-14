package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

@Getter
public class UpdateImagemUserRequest {

    private String base64;

    public UpdateImagemUserRequest(String base64) {
        this.base64 = base64;
    }
}
