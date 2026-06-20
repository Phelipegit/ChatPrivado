package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

@Getter
public class CriarMensagemRequest {
    private String message;

    public CriarMensagemRequest(String message) {
        this.message = message;
    }

}
