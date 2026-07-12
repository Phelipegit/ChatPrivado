package ProjetoPhelipe.ChatPrivado.service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class EmailService {

    private final Resend resend;
    private final RedisTemplate<String,Object> redisTemplate;

    public EmailService(@Value("${KEY_API_RESEND}") String API_KEY,RedisTemplate<String,Object> redisTemplate) {
        this.resend = new Resend(API_KEY);
        this.redisTemplate = redisTemplate;
    }

    public void enviarEmail(String destinatario,String subject,String html) {
        UUID token = UUID.randomUUID();

        redisTemplate.opsForValue().set(destinatario,token, Duration.ofMinutes(10));

        CreateEmailOptions params = CreateEmailOptions.builder().from("redefinicaoSenha@phelipedev.com.br")
                .to(destinatario)
                .subject(subject)
                .html(html)
                .build();

        try {
            resend.emails().send(params);
        } catch (ResendException e) {
            throw new RuntimeException(e);
        }
    }
}
