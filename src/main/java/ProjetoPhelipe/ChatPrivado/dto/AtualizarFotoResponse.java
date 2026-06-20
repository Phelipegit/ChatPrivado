package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

@Getter
public class AtualizarFotoResponse {

    private Boolean success;

    public AtualizarFotoResponse(boolean success) {
        this.success = success;
    }
}
