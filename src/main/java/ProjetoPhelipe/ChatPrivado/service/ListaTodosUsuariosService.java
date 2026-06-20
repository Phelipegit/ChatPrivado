package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.CriarMensagemResponse;
import ProjetoPhelipe.ChatPrivado.dto.UserSummary;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ListaTodosUsuariosService {

    private final RepositoryUser repositoryUser;

    public ListaTodosUsuariosService(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    public List<UserSummary> listarTodosUsuarios() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            return new ArrayList<>();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<EntityUser> userR = repositoryUser.findByEmail(userDetails.getUsername());

        if(userR.isEmpty()) {
            return new ArrayList<>();
        }

        EntityUser user = userR.get();

        return repositoryUser.
                findAll().
                stream().filter(entity -> !entity.getId().equals(user.getId())).map(entity -> new UserSummary(entity.getUsername(),entity.getPerfilImagem())).toList();
    }
}
