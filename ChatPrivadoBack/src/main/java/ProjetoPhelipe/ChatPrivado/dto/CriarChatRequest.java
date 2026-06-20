package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

@Getter
public class CriarChatRequest {

    private String userUsername;

    public CriarChatRequest(String userUsername) {
        this.userUsername = userUsername;
    }
}
