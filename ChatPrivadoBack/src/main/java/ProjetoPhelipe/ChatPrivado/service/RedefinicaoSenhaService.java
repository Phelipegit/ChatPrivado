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

        Boolean existKeyEmail = redisTemplate.hasKey(request.getEmail().trim().toLowerCase());

        if(existKeyEmail) {
            return ResponseEntity.status(429).body(new UserUpdatePasswordResponse("E-mail já enviado, tente novamente em alguns minutos",false));
        }

        String token = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set("reset-password:" + request.getEmail().trim().toLowerCase(),token, Duration.ofMinutes(10));

        String url = "https://app.phelipedev.com.br/updatePassword/" + token + "?email=" + request.getEmail().trim().toLowerCase();

        String html = "<h1>Redefinição de Senha</h1>" +
                "<p>Seu link expira em 10 minutos </p>" +
                "<a href=\"" + url + "\">Clique aqui</a>";

        emailService.enviarEmail(request.getEmail().trim().toLowerCase(),"Redefinição de Senha",html);

        return ResponseEntity.ok().body(new UserUpdatePasswordResponse("Um link para redefinir a senha foi enviado para seu e-mail",true));
    }
}
