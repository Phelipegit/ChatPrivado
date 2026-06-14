package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.RecordUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class ProcessarBase64ToPng {

    @Value("${URL_API}")
    private String URL_API;

    @Value("${UPLOAD_PRESET}")
    private String uploadPreset;

    private final ObjectMapper objectMapper;

    public ProcessarBase64ToPng(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String enviarReq(String imagem64) throws IOException, InterruptedException {

        String imagemCompleta = "data:image/png;base64," + imagem64;

        String body = "file=" + URLEncoder.encode(imagemCompleta, StandardCharsets.UTF_8)
                + "&upload_preset=" + URLEncoder.encode(uploadPreset, StandardCharsets.UTF_8);
        HttpClient httpClient = HttpClient.newBuilder().build();


        HttpRequest httpRequest = HttpRequest.newBuilder().
                uri(URI.create(URL_API)).
                header("Content-Type","application/x-www-form-urlencoded").
                POST(HttpRequest.BodyPublishers.ofString(body)).
                build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        RecordUrl record = objectMapper.readValue(response.body(),RecordUrl.class);

        return record.secure_url();
    }
}
