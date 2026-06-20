package ProjetoPhelipe.ChatPrivado.controller;

import ProjetoPhelipe.ChatPrivado.dto.CriarMensagemRequest;
import ProjetoPhelipe.ChatPrivado.dto.CriarMensagemResponse;
import ProjetoPhelipe.ChatPrivado.service.CriarMensagemService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.UUID;

@Controller
public class WebSocketController {
    private CriarMensagemService criarMensagemService;

    public WebSocketController(CriarMensagemService criarMensagemService) {
        this.criarMensagemService = criarMensagemService;
    }

    @MessageMapping("/chat/{id}")
    @SendTo("/topic/chat/{id}")
    public CriarMensagemResponse enviarMensagem(@DestinationVariable UUID id, @Payload CriarMensagemRequest request,Principal principal) {
        return criarMensagemService.criarMensagemService(request,id,principal);
    }
}
