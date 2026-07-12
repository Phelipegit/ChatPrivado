package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.UserUpdatePasswordRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RedefinicaoSenhaService {

    private EmailService emailService;

    public RedefinicaoSenhaService(EmailService emailService) {
        this.emailService = emailService;
    }


    public void redefinirSenha(UserUpdatePasswordRequest request) {
        UUID token = UUID.randomUUID();

        String url = "https://app.phelipedev.com.br/updatePassword/" + token;

        String html = "<h1>Redefinição de Senha</h1>" +
                "<p>Seu link expira em 10 minutos </p>" +
                "<a href=\"" + url + "\">Clique aqui</a>";

        emailService.enviarEmail(request.getEmail().trim().toLowerCase(),"Redefinição de Senha",html);
    }
}
