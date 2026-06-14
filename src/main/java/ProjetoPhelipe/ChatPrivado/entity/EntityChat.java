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

    @OneToMany(mappedBy ="entityChat",cascade = CascadeType.ALL)
    private List<EntityMessage> entityMessageList = new ArrayList<>();

    @ManyToOne()
    private EntityUser user_id;

    public EntityChat() {

    }
}
