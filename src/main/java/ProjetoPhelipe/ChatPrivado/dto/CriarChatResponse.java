package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CriarChatResponse {

    private Boolean success;

    private UUID chat_id;

    public CriarChatResponse(Boolean success,UUID chat_id) {
        this.success = success;
        this.chat_id = chat_id;
    }
}
