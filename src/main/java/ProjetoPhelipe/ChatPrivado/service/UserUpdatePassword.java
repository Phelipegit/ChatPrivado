package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.UserUpdatePasswordRequest;
import ProjetoPhelipe.ChatPrivado.dto.UserUpdatePasswordResponse;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UserUpdatePassword {

    private final RepositoryUser repositoryUser;
    private final PasswordEncoder passwordEncoder;

    public UserUpdatePassword(RepositoryUser repositoryUser,PasswordEncoder passwordEncoder) {
        this.repositoryUser = repositoryUser;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<UserUpdatePasswordResponse> updatePassword(UserUpdatePasswordRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body(new UserUpdatePasswordResponse("Erro ao validar usuário",false));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if(request.getNewPassword().length() < 8) {
            return ResponseEntity.badRequest().body(new UserUpdatePasswordResponse("Senha nova precisa ter no mínimo 8 dígitos",false));
        }

        if(!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            return ResponseEntity.badRequest().body(new UserUpdatePasswordResponse("A nova senha e a confirmação não são iguais",false));
        }

        Optional<EntityUser> entityUser = repositoryUser.findByEmail(userDetails.getUsername());

        if(entityUser.isEmpty()) {
            return ResponseEntity.badRequest().body(new UserUpdatePasswordResponse("Usuário não encontrado",false));
        }

        EntityUser user = entityUser.get();

        if(!passwordEncoder.matches(request.getAtualPassword(),user.getPassword())) {
            return ResponseEntity.badRequest().body(new UserUpdatePasswordResponse("Senha atual não se confere",false));
        }

        String newPassword = passwordEncoder.encode(request.getNewPassword());

        user.setPassword(newPassword);

        repositoryUser.save(user);

        return ResponseEntity.ok(new UserUpdatePasswordResponse("Senha alterada com sucesso",true));
    }
}
