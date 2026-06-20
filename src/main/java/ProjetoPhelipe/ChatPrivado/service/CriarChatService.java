package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.CriarChatRequest;
import ProjetoPhelipe.ChatPrivado.dto.CriarChatResponse;
import ProjetoPhelipe.ChatPrivado.entity.EntityChat;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryChat;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryUser;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class CriarChatService {

    private final RepositoryUser repositoryUser;
    private final RepositoryChat repositoryChat;

    public CriarChatService(RepositoryUser repositoryUser,RepositoryChat repositoryChat) {
        this.repositoryUser = repositoryUser;
        this.repositoryChat = repositoryChat;
    }

    @Transactional
    public ResponseEntity<CriarChatResponse> criarChat(@RequestBody CriarChatRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body(new CriarChatResponse(false,null));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<EntityUser> existUserAut = repositoryUser.findByEmail(userDetails.getUsername());

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

        EntityChat entityChat = new EntityChat();

        repositoryChat.save(entityChat);

        userAut.getChats().add(entityChat);
        userRequest.getChats().add(entityChat);

        repositoryUser.save(userAut);
        repositoryUser.save(userRequest);

        return ResponseEntity.ok(new CriarChatResponse(true,entityChat.getId()));
    }
}
