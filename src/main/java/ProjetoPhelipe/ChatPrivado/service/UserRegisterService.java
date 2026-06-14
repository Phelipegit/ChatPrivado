package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.UserRegisterRequest;
import ProjetoPhelipe.ChatPrivado.dto.UserRegisterResponse;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRegisterService {

    private final RepositoryUser repositoryUser;
    private final PasswordEncoder passwordEncoder;

    public UserRegisterService(RepositoryUser repositoryUser,PasswordEncoder passwordEncoder) {
        this.repositoryUser = repositoryUser;
        this.passwordEncoder = passwordEncoder;
    }
    public ResponseEntity<UserRegisterResponse> userRegister(UserRegisterRequest request) {
        if(request.getUsername().length() < 5) {
            return ResponseEntity.badRequest().body(new UserRegisterResponse(false, "Username precisa ter no mínimo 5 dígitos"));
        }

        if(request.getPassword().length() < 8) {
            return ResponseEntity.badRequest().body(new UserRegisterResponse(false, "Senha precisa ter no mínimo 8 dígitos"));
        }

        Optional<EntityUser> usernameExist = repositoryUser.findByUsername(request.getUsername().toLowerCase());

        if(usernameExist.isPresent()) {
            return ResponseEntity.badRequest().body(new UserRegisterResponse(false, "Username já está sendo utilizado"));
        }

        Optional<EntityUser> emailExist = repositoryUser.findByEmail(request.getEmail().toLowerCase());

        if(emailExist.isPresent()) {
            return ResponseEntity.badRequest().body(new UserRegisterResponse(false, "Email já está sendo utilizado"));
        }

        String passwordHash = passwordEncoder.encode(request.getPassword());

        EntityUser entityUser = new EntityUser(request.getEmail().toLowerCase(),request.getUsername().toLowerCase(),passwordHash);

        repositoryUser.save(entityUser);

        return ResponseEntity.ok(new UserRegisterResponse(true, "Usuário registrado com sucesso"));
    }
}
