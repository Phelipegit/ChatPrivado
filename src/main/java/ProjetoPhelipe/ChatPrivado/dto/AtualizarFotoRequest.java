package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

@Getter
public class AtualizarFotoRequest {

    String iBase64;

    public AtualizarFotoRequest(String iBase64) {
        this.iBase64 = iBase64;
    }
}
