package ProjetoPhelipe.ChatPrivado.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
public class EntityMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String message;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne()
    @JoinColumn(name = "id_chat")
    private EntityChat chat;

    @ManyToOne()
    @JoinColumn(name = "user_sender")
    private EntityUser sender;

    public EntityMessage(String message,EntityChat chat,EntityUser entityUser) {
        this.message = message;
        this.chat = chat;
        this.sender = entityUser;
    }

    public EntityMessage() {

    }

}
