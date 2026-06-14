package ProjetoPhelipe.ChatPrivado.repository;

import ProjetoPhelipe.ChatPrivado.entity.EntityUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryUser extends JpaRepository<EntityUser, UUID> {
    Optional<EntityUser> findByUsername(String username);
    Optional<EntityUser> findByEmail(String email);
}
