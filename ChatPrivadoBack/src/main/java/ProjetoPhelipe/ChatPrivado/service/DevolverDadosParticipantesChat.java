package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.DDPUser;
import ProjetoPhelipe.ChatPrivado.entity.EntityChat;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryChat;
import ProjetoPhelipe.ChatPrivado.security.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DevolverDadosParticipantesChat {

    private final RepositoryChat repositoryChat;
    private final AuthUser authUser;

    public DevolverDadosParticipantesChat(RepositoryChat repositoryChat,AuthUser authUser) {
        this.repositoryChat = repositoryChat;
        this.authUser = authUser;
    }

    public ResponseEntity<List<DDPUser>> devolverUsuarios(UUID id_chat) {
        Optional<EntityUser> user = authUser.getUsuarioAutenticado();

        if(user.isEmpty()) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }

        Optional<EntityChat> entityChat = repositoryChat.findById(id_chat);

        if(entityChat.isEmpty()) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }

        EntityChat chat = entityChat.get();

        if(!chat.getUsuarios().contains(user.get())) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }

        return ResponseEntity.ok(chat.getUsuarios().stream().map(e -> new DDPUser(e.getUsername(),e.getPerfilImagem(),true)).toList());
    }

}
