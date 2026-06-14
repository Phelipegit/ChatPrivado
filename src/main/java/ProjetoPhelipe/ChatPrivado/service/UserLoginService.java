package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.UserLoginRequest;
import ProjetoPhelipe.ChatPrivado.dto.UserLoginResponse;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryUser;
import ProjetoPhelipe.ChatPrivado.security.ArgonConfig;
import ProjetoPhelipe.ChatPrivado.service.JWT.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoginService {

    private final RepositoryUser repositoryUser;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserLoginService(RepositoryUser repositoryUser, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repositoryUser = repositoryUser;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    public ResponseEntity<UserLoginResponse> userLogin(UserLoginRequest request) {
        Optional<EntityUser> exist = repositoryUser.findByEmail(request.getEmail());

        if(exist.isEmpty()) {
            return ResponseEntity.badRequest().body(new UserLoginResponse("Usuário não existe",false,null));
        }

        EntityUser entityUser = exist.get();

        if(!passwordEncoder.matches(request.getPassword(),entityUser.getPassword())) {
            return ResponseEntity.badRequest().body(new UserLoginResponse("Senha incorreta",false,null));
        }

        String token = jwtService.generateToken(request.getEmail());

        return ResponseEntity.ok(new UserLoginResponse("Usuário logado com sucesso",true,token));
    }
}
