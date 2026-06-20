package ProjetoPhelipe.ChatPrivado.repository;

import ProjetoPhelipe.ChatPrivado.entity.EntityChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryChat extends JpaRepository<EntityChat, UUID> {

}
