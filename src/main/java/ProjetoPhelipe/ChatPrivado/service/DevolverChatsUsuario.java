package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.DDPChats;
import ProjetoPhelipe.ChatPrivado.entity.EntityChat;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryChat;
import ProjetoPhelipe.ChatPrivado.security.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DevolverChatsUsuario {

    private final AuthUser authUser;

    private DevolverChatsUsuario(AuthUser authUser) {
        this.authUser = authUser;
    }

    public ResponseEntity<List<DDPChats>> devolverChatsUsuario() {
        Optional<EntityUser> optionalEntityUser = authUser.getUsuarioAutenticado();

        if(optionalEntityUser.isEmpty()) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }

        EntityUser entityUser = optionalEntityUser.get();

        return ResponseEntity.ok(entityUser.getChats().stream().map(chat -> {
            EntityUser outro = chat.getUsuarios().stream().filter(u -> !u.getId().equals(entityUser.getId())).findFirst().orElseThrow();

            String ultimaMsg = "";

            if(chat.getMessageList().isEmpty()) {
                ultimaMsg = null;
            } else {
                ultimaMsg = chat.getMessageList().getLast().getMessage();
            }

            return new DDPChats(chat.getId(),outro.getUsername(),outro.getPerfilImagem(),ultimaMsg);
        }).toList());
    }
}
