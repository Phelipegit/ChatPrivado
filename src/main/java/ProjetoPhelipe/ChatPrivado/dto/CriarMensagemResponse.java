package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CriarMensagemResponse {

    private Boolean success;

    private String message;

    private String userSender;

    private LocalDateTime creatAt;

    public CriarMensagemResponse(Boolean success,String message,String userSender,LocalDateTime creatAt) {
        this.success = success;
        this.message = message;
        this.userSender = userSender;
        this.creatAt = creatAt;
    }
}
