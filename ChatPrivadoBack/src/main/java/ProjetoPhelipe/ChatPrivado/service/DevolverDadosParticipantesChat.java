package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.DDPUser;
import ProjetoPhelipe.ChatPrivado.entity.EntityChat;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryChat;
import ProjetoPhelipe.ChatPrivado.security.AuthUser;
import org.springframework.http.ResponseEntity;
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

        Optional<EntityUser> userReq = authUser.getUsuarioAutenticado();

        if(userReq.isEmpty()) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }

        EntityUser entityUserReq = userReq.get();

        Optional<EntityChat> existEntityChat = repositoryChat.findById(id_chat);

        if(existEntityChat.isEmpty()) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }

        EntityChat entityChat = existEntityChat.get();

        if(!entityChat.getUsuarios().contains(entityUserReq)) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }

        return ResponseEntity.ok(entityChat.getUsuarios().stream().map(element -> new DDPUser(element.getUsername(),element.getPerfilImagem(),true)).toList());
    }

}
