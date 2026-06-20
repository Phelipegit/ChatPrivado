package ProjetoPhelipe.ChatPrivado.repository;

import ProjetoPhelipe.ChatPrivado.entity.EntityMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryMessage extends JpaRepository<EntityMessage, UUID> {
}
