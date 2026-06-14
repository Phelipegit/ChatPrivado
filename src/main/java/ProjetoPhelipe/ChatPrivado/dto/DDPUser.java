package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

@Getter
public class DDPUser {

    private String username;

    private String imagem_url;

    private Boolean success;

    public DDPUser(String username, String imagem_url,Boolean success) {
        this.username = username;
        this.imagem_url = imagem_url;
        this.success = success;
    }
}
