package ProjetoPhelipe.ChatPrivado.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class EntityChat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToMany(mappedBy = "chats")
    private List<EntityUser> usuarios = new ArrayList<>();

    @OneToMany(mappedBy ="chat",cascade = CascadeType.ALL)
    private List<EntityMessage> messageList = new ArrayList<>();

    public EntityChat() {

    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EntityChat chat) {
            return this.id.equals(chat.getId());
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
