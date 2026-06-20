package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.DDPUser;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DevolverDadosQualquerUsuario {

    private final RepositoryUser repositoryUser;

    public DevolverDadosQualquerUsuario(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    public ResponseEntity<DDPUser> devolverDadosQualquerUsuario(String username) {
        Optional<EntityUser> exist = repositoryUser.findByUsername(username.toLowerCase());

        if(exist.isEmpty()) {
            return ResponseEntity.badRequest().body(new DDPUser(null,null,false));
        }

        EntityUser user = exist.get();

        return ResponseEntity.ok(new DDPUser(user.getUsername(),user.getPerfilImagem(),true));
    }
}
