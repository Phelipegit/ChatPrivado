package ProjetoPhelipe.ChatPrivado.security;

import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthUser {

    private RepositoryUser repositoryUser;

    public AuthUser(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    public Optional<EntityUser> getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return repositoryUser.findByEmail(userDetails.getUsername());
    }
}
