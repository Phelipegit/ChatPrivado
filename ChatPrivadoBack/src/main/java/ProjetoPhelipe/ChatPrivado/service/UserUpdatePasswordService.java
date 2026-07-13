package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.UserUpdatePasswordRequest;
import ProjetoPhelipe.ChatPrivado.dto.UserUpdatePasswordResponse;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryUser;
import jakarta.transaction.Transactional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserUpdatePasswordService {

    private final RepositoryUser repositoryUser;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String,Object> redisTemplate;

    public UserUpdatePasswordService(RepositoryUser repositoryUser, PasswordEncoder passwordEncoder,RedisTemplate<String,Object> redisTemplate) {
        this.repositoryUser = repositoryUser;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public ResponseEntity<UserUpdatePasswordResponse> updatePassword(UserUpdatePasswordRequest request) {

        Object valueToken = redisTemplate.opsForValue().get(request.getToken());

        if(valueToken == null) {
            return ResponseEntity.badRequest().body(new UserUpdatePasswordResponse(null, false));
        }

        if(!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            return ResponseEntity.badRequest().body(new UserUpdatePasswordResponse("As senhas não são iguais",false));
        }

        if(request.getNewPassword().length() < 8) {
            return ResponseEntity.badRequest().body(new UserUpdatePasswordResponse("A senha nova precisa ter no mínimo 8 dígitos",false));
        }

        Optional<EntityUser> entity = repositoryUser.findByEmail((String) valueToken);

        if(entity.isEmpty()) {
            return ResponseEntity.badRequest().body(new UserUpdatePasswordResponse(null, false));
        }

        if(passwordEncoder.matches(request.getAtualPassword(),entity.get().getPassword())) {
            return ResponseEntity.badRequest().body(new UserUpdatePasswordResponse("A senha atual e a nova senha são iguais",false));
        }

        redisTemplate.delete(request.getToken());

        EntityUser entityUser = entity.get();

        entityUser.setPassword(passwordEncoder.encode(request.getNewPassword()));

        repositoryUser.save(entityUser);

        return ResponseEntity.ok(new UserUpdatePasswordResponse("Senha atualizada com sucesso",true));
    }
}
