package ProjetoPhelipe.ChatPrivado.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class EntityUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true,nullable = false)
    private String username;

    @Column(nullable = false)
    @Setter
    private String password;

    @Column(nullable = false)
    @Setter
    private String perfilImagem = "https://res.cloudinary.com/dcvnvdjlb/image/upload/v1779067143/573323465_1219825463302212_7278921664109726296_n_irmghp.jpg";

    @CreationTimestamp
    private LocalDateTime localDateTime;

    @OneToMany(mappedBy = "user_id",cascade = CascadeType.ALL)
    private List<EntityChat> entityChatList = new ArrayList<>();


    public EntityUser(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public EntityUser() {

    }
}
