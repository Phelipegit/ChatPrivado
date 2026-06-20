package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.AtualizarFotoRequest;
import ProjetoPhelipe.ChatPrivado.dto.AtualizarFotoResponse;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryUser;
import ProjetoPhelipe.ChatPrivado.security.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class AtualizarImagemUsuarioService {

    private final AuthUser authUser;
    private final ProcessarBase64ToPngService processarBase64ToPngService;
    private final RepositoryUser repositoryUser;

    public AtualizarImagemUsuarioService(AuthUser authUser,ProcessarBase64ToPngService processarBase64ToPngService, RepositoryUser repositoryUser) {
        this.authUser = authUser;
        this.processarBase64ToPngService = processarBase64ToPngService;
        this.repositoryUser = repositoryUser;
    }

    public ResponseEntity<AtualizarFotoResponse> atualizarImagemUsuario(AtualizarFotoRequest request) throws IOException, InterruptedException {

        Optional<EntityUser> giveEntity = authUser.getUsuarioAutenticado();

        if(giveEntity.isEmpty()) {
            return ResponseEntity.badRequest().body(new AtualizarFotoResponse(false));
        }

        EntityUser entityUser = giveEntity.get();

        String urlNew = processarBase64ToPngService.enviarReq(request.getBase64());

        entityUser.setPerfilImagem(urlNew);

        repositoryUser.save(entityUser);

        return ResponseEntity.ok(new AtualizarFotoResponse(true));
    }

}
