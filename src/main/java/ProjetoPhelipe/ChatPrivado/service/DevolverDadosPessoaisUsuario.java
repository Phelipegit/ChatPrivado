package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.DDPUser;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryUser;
import ProjetoPhelipe.ChatPrivado.security.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DevolverDadosPessoaisUsuario {

    private final RepositoryUser repositoryUser;
    private final AuthUser authUser;

    public DevolverDadosPessoaisUsuario(RepositoryUser repositoryUser,AuthUser authUser) {
        this.repositoryUser = repositoryUser;
        this.authUser = authUser;
    }

    public ResponseEntity<DDPUser> devolverDadosPessoais() {

        Optional<EntityUser> userEntity = authUser.getUsuarioAutenticado();

        if(userEntity.isEmpty()) {
            return ResponseEntity.badRequest().body(new DDPUser(null,null,false));
        }

        EntityUser user = userEntity.get();

        return ResponseEntity.ok(new DDPUser(user.getUsername(),user.getPerfilImagem(),true));
    }
}
