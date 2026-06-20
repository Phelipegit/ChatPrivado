package ProjetoPhelipe.ChatPrivado.service;
import ProjetoPhelipe.ChatPrivado.dto.DDPDevolverMensagensChat;
import ProjetoPhelipe.ChatPrivado.entity.EntityChat;
import ProjetoPhelipe.ChatPrivado.entity.EntityMessage;
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
public class DevolverMensagensChatService {

    private RepositoryChat repositoryChat;
    private AuthUser authUser;

    public DevolverMensagensChatService(RepositoryChat repositoryChat, AuthUser authUser) {
        this.repositoryChat = repositoryChat;
        this.authUser = authUser;
    }

    public ResponseEntity<List<DDPDevolverMensagensChat>> devolverMensagensChat(UUID id_chat) {

        Optional<EntityUser> entity = authUser.getUsuarioAutenticado();

        if(entity.isEmpty()) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }

        EntityUser user = entity.get();

        Optional<EntityChat> optionalEntityChat = repositoryChat.findById(id_chat);

        if(optionalEntityChat.isEmpty()) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }

        EntityChat entityChat = optionalEntityChat.get();

        if(!entityChat.getUsuarios().contains(user)) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
        return ResponseEntity.ok(entityChat.getMessageList().stream().map(element -> new DDPDevolverMensagensChat(element.getId(),
                element.getMessage(),element.getSender().getUsername(),element.getCreatedAt())).toList());
    }
}
