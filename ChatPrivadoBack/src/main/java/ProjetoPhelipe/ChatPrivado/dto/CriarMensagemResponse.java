package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class CriarMensagemResponse {

    private UUID id;

    private Boolean success;

    private String message;

    private String userSender;

    private LocalDateTime createAt;

    public CriarMensagemResponse(UUID id,Boolean success,String message,String userSender,LocalDateTime createAt) {
        this.id = id;
        this.success = success;
        this.message = message;
        this.userSender = userSender;
        this.createAt = createAt;
    }
}
