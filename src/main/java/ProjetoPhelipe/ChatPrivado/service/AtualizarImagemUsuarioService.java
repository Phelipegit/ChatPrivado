package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.AtualizarFotoRequest;
import ProjetoPhelipe.ChatPrivado.dto.AtualizarFotoResponse;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class AtualizarImagemUsuarioService {

    private final ProcessarBase64ToPngService processarBase64ToPngService;
    private final RepositoryUser repositoryUser;

    public AtualizarImagemUsuarioService(ProcessarBase64ToPngService processarBase64ToPngService, RepositoryUser repositoryUser) {
        this.processarBase64ToPngService = processarBase64ToPngService;
        this.repositoryUser = repositoryUser;
    }

    public ResponseEntity<AtualizarFotoResponse> atualizarImagemUsuario(AtualizarFotoRequest request) throws IOException, InterruptedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body(new AtualizarFotoResponse(false));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<EntityUser> giveEntity = repositoryUser.findByEmail(userDetails.getUsername());

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
