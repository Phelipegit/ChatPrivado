package ProjetoPhelipe.ChatPrivado.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DDPChats {
    private UUID id_chat;

    private String usernameOutro;

    private String perfil_imagem;

    private String ultimaMsg;

    public DDPChats(UUID id_chat,String usernameOutro,String perfil_imagem,String ultimaMsg) {
        this.id_chat = id_chat;
        this.usernameOutro = usernameOutro;
        this.perfil_imagem = perfil_imagem;
        this.ultimaMsg = ultimaMsg;
    }
}
