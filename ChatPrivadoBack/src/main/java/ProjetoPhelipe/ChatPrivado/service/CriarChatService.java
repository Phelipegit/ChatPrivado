package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.CriarChatRequest;
import ProjetoPhelipe.ChatPrivado.dto.CriarChatResponse;
import ProjetoPhelipe.ChatPrivado.entity.EntityChat;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryChat;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryUser;
import ProjetoPhelipe.ChatPrivado.security.AuthUser;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CriarChatService {

    private final RepositoryUser repositoryUser;
    private final RepositoryChat repositoryChat;
    private final AuthUser authUser;

    public CriarChatService(RepositoryUser repositoryUser,RepositoryChat repositoryChat,AuthUser authUser) {
        this.repositoryUser = repositoryUser;
        this.repositoryChat = repositoryChat;
        this.authUser = authUser;
    }

    @Transactional
    public ResponseEntity<CriarChatResponse> criarChat(CriarChatRequest request) {
        Optional<EntityUser> existUserAut = authUser.getUsuarioAutenticado();

        if(existUserAut.isEmpty()) {
            return ResponseEntity.badRequest().body(new CriarChatResponse(false,null));
        }

        Optional<EntityUser> existUserRequest = repositoryUser.findByUsername(request.getUserUsername());

        if(existUserRequest.isEmpty()) {
            return ResponseEntity.badRequest().body(new CriarChatResponse(false,null));
        }

        EntityUser userAut = existUserAut.get();

        EntityUser userRequest = existUserRequest.get();

        if(userRequest.getId().equals(userAut.getId())) {
            return ResponseEntity.badRequest().body(new CriarChatResponse(false,null));
        }

        Optional<EntityChat> OptionalEntityChatExist = repositoryChat.findAll().stream().filter(element -> element.getUsuarios().contains(userAut) && element.getUsuarios().contains(userRequest)).findFirst();

        if(OptionalEntityChatExist.isPresent()) {
            EntityChat entityChatExist = OptionalEntityChatExist.get();

            return ResponseEntity.ok(new CriarChatResponse(true,entityChatExist.getId()));
        }

        EntityChat entityChat = new EntityChat();

        repositoryChat.save(entityChat);

        userAut.getChats().add(entityChat);
        userRequest.getChats().add(entityChat);

        repositoryUser.save(userAut);
        repositoryUser.save(userRequest);

        return ResponseEntity.ok(new CriarChatResponse(true,entityChat.getId()));
    }
}
