package ProjetoPhelipe.ChatPrivado.controller;

import ProjetoPhelipe.ChatPrivado.dto.*;
import ProjetoPhelipe.ChatPrivado.service.RedefinicaoSenhaService;
import ProjetoPhelipe.ChatPrivado.service.UserLoginService;
import ProjetoPhelipe.ChatPrivado.service.UserRegisterService;
import ProjetoPhelipe.ChatPrivado.service.UserUpdatePasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRegisterService userRegisterService;
    private final UserLoginService userLoginService;
    private final RedefinicaoSenhaService redefinicaoSenhaService;
    private final UserUpdatePasswordService userUpdatePasswordService;

    public AuthController(UserRegisterService userRegisterService,
                          UserLoginService userLoginService,
                          RedefinicaoSenhaService redefinicaoSenhaService,
                          UserUpdatePasswordService userUpdatePasswordService) {
        this.userRegisterService = userRegisterService;
        this.userLoginService = userLoginService;
        this.redefinicaoSenhaService = redefinicaoSenhaService;
        this.userUpdatePasswordService = userUpdatePasswordService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> userRegister(@RequestBody UserRegisterRequest request) {
        return userRegisterService.userRegister(request);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> userLogin(@RequestBody UserLoginRequest request) {
        return userLoginService.userLogin(request);
    }

    @PostMapping("/password/sendEmail")
    public void enviarEmailResetPassword(@RequestBody UserUpdatePasswordRequest request) {
        redefinicaoSenhaService.redefinirSenha(request);
    }

    @PutMapping("/password/update")
    public ResponseEntity<UserUpdatePasswordResponse> updatePassword(@RequestBody UserUpdatePasswordRequest request) {]

    }
}
