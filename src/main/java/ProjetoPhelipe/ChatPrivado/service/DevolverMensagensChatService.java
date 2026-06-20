package ProjetoPhelipe.ChatPrivado.service;
import ProjetoPhelipe.ChatPrivado.entity.EntityChat;
import ProjetoPhelipe.ChatPrivado.entity.EntityMessage;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryChat;
import ProjetoPhelipe.ChatPrivado.security.AuthUser;
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

    public List<EntityMessage> devolverMensagensChat(UUID id_chat) {

        Optional<EntityUser> entity = authUser.getUsuarioAutenticado();

        if(entity.isEmpty()) {
            return new ArrayList<>();
        }

        EntityUser user = entity.get();

        Optional<EntityChat> optionalEntityChat = repositoryChat.findById(id_chat);

        if(optionalEntityChat.isEmpty()) {
            return new ArrayList<>();
        }

        EntityChat entityChat = optionalEntityChat.get();

        if(!entityChat.getUsuarios().contains(user)) {
            return new ArrayList<>();
        }

        return entityChat.getMessageList().stream().toList();
    }
}
