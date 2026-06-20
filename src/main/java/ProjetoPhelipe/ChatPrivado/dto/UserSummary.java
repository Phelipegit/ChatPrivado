package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

@Getter
public class UserSummary {

    private String username;

    private String imagem_url;

    public UserSummary(String username, String imagem_url) {
        this.username = username;
        this.imagem_url = imagem_url;
    }
}
