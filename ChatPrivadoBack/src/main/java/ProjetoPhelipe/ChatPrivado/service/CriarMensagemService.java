package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.CriarMensagemRequest;
import ProjetoPhelipe.ChatPrivado.dto.CriarMensagemResponse;
import ProjetoPhelipe.ChatPrivado.entity.EntityChat;
import ProjetoPhelipe.ChatPrivado.entity.EntityMessage;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryChat;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryMessage;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryUser;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
public class CriarMensagemService {

    private RepositoryMessage repositoryMessage;
    private RepositoryChat repositoryChat;
    private RepositoryUser repositoryUser;

    public CriarMensagemService(RepositoryMessage repositoryMessage,RepositoryChat repositoryChat,RepositoryUser repositoryUser) {
        this.repositoryMessage = repositoryMessage;
        this.repositoryChat = repositoryChat;
        this.repositoryUser = repositoryUser;
    }

    @Transactional
    public CriarMensagemResponse criarMensagemService(CriarMensagemRequest request, UUID chat_id,Principal principal) {

        Optional<EntityUser> existUserAut = repositoryUser.findByEmail(principal.getName());

        if(existUserAut.isEmpty()) {
            return new CriarMensagemResponse(null,null,null,null,null);
        }

        EntityUser user = existUserAut.get();

        Optional<EntityChat> existChat = repositoryChat.findById(chat_id);

        if(existChat.isEmpty()) {
            return new CriarMensagemResponse(null,null,null,null,null);
        }

        EntityChat chat = existChat.get();

        if(!chat.getUsuarios().contains(user)) {
            return new CriarMensagemResponse(null,null,null,null,null);
        }

        EntityMessage entityMessage = new EntityMessage(request.getMessage(),chat,user);

        repositoryMessage.save(entityMessage);

        return new CriarMensagemResponse(entityMessage.getId(),true,entityMessage.getMessage(),user.getUsername(),entityMessage.getCreatedAt());
    }
}
