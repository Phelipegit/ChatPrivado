package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.UserUpdatePasswordRequest;
import ProjetoPhelipe.ChatPrivado.dto.UserUpdatePasswordResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class RedefinicaoSenhaService {

    private EmailService emailService;
    private RedisTemplate<String,Object> redisTemplate;

    public RedefinicaoSenhaService(EmailService emailService,RedisTemplate<String,Object> redisTemplate) {
        this.emailService = emailService;
        this.redisTemplate = redisTemplate;
    }

    public ResponseEntity<UserUpdatePasswordResponse> redefinirSenha(UserUpdatePasswordRequest request) {

        String token = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(token,request.getEmail().trim().toLowerCase(), Duration.ofMinutes(10));

        String url = "https://app.phelipedev.com.br/updatePassword/" + token;

        String html = "<h1>Redefinição de Senha</h1>" +
                "<p>Seu link expira em 10 minutos </p>" +
                "<a href=\"" + url + "\">Clique aqui</a>";

        emailService.enviarEmail(request.getEmail().trim().toLowerCase(),"Redefinição de Senha",html);

        return ResponseEntity.ok().body(new UserUpdatePasswordResponse(null,true));
    }
}
