package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

@Getter
public class AtualizarFotoRequest {

    String base64;

    public AtualizarFotoRequest(String base64) {
        this.base64 = base64;
    }
}
