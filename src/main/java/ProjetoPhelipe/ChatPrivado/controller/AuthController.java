package ProjetoPhelipe.ChatPrivado.controller;

import ProjetoPhelipe.ChatPrivado.dto.UserLoginRequest;
import ProjetoPhelipe.ChatPrivado.dto.UserLoginResponse;
import ProjetoPhelipe.ChatPrivado.dto.UserRegisterRequest;
import ProjetoPhelipe.ChatPrivado.dto.UserRegisterResponse;
import ProjetoPhelipe.ChatPrivado.service.UserLoginService;
import ProjetoPhelipe.ChatPrivado.service.UserRegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRegisterService userRegisterService;
    private final UserLoginService userLoginService;

    public AuthController(UserRegisterService userRegisterService, UserLoginService userLoginService) {
        this.userRegisterService = userRegisterService;
        this.userLoginService = userLoginService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> userRegister(@RequestBody UserRegisterRequest request) {
        return userRegisterService.userRegister(request);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> userLogin(@RequestBody UserLoginRequest request) {
        return userLoginService.userLogin(request);
    }
}
