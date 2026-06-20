package ProjetoPhelipe.ChatPrivado.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class DDPDevolverMensagensChat {

    private UUID id;

    private String message;

    private String userSender;

    private LocalDateTime createAt;
}
