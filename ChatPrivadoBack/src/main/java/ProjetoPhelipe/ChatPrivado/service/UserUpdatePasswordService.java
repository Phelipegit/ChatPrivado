package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.UserUpdatePasswordRequest;
import ProjetoPhelipe.ChatPrivado.dto.UserUpdatePasswordResponse;
import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryUser;
import jakarta.transaction.Transactional;
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

    public UserUpdatePasswordService(RepositoryUser repositoryUser, PasswordEncoder passwordEncoder) {
        this.repositoryUser = repositoryUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ResponseEntity<UserUpdatePasswordResponse> updatePassword(UserUpdatePasswordRequest request) {
    }
}
