package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.DDPUser;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryUser;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class DevolverDadosPessoaisUsuario {

    private final RepositoryUser repositoryUser;

    public DevolverDadosPessoaisUsuario(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    public ResponseEntity<DDPUser> devolverDadosPessoais() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body(new DDPUser(null, null, false));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<EntityUser> userEntity = repositoryUser.findByEmail(userDetails.getUsername());

        if(userEntity.isEmpty()) {
            return ResponseEntity.badRequest().body(new DDPUser(null,null,false));
        }

        EntityUser user = userEntity.get();

        return ResponseEntity.ok(new DDPUser(user.getUsername(),user.getPerfilImagem(),true));
    }
}
