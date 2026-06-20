package ProjetoPhelipe.ChatPrivado.controller;

import ProjetoPhelipe.ChatPrivado.dto.*;
import ProjetoPhelipe.ChatPrivado.entity.EntityChat;
import ProjetoPhelipe.ChatPrivado.entity.EntityMessage;
import ProjetoPhelipe.ChatPrivado.service.*;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/u")
public class UserController {

    private final AtualizarImagemUsuarioService atualizarImagemUsuarioService;
    private final UserUpdatePasswordService userUpdatePasswordService;
    private final DevolverDadosQualquerUsuario devolverDadosQualquerUsuario;
    private final DevolverDadosPessoaisUsuario devolverDadosPessoaisUsuario;
    private final ListaTodosUsuariosService listaTodosUsuariosService;
    private final CriarChatService criarChatService;
    private final DevolverMensagensChatService devolverMensagensChatService;

    public UserController(AtualizarImagemUsuarioService atualizarImagemUsuarioService,
                          UserUpdatePasswordService userUpdatePasswordService,
                          DevolverDadosQualquerUsuario devolverDadosQualquerUsuario ,
                          DevolverDadosPessoaisUsuario devolverDadosPessoaisUsuario,
                          ListaTodosUsuariosService listaTodosUsuariosService,
                          CriarChatService criarChatService,
                          DevolverMensagensChatService devolverMensagensChatService) {
        this.atualizarImagemUsuarioService = atualizarImagemUsuarioService;
        this.userUpdatePasswordService = userUpdatePasswordService;
        this.devolverDadosQualquerUsuario = devolverDadosQualquerUsuario;
        this.devolverDadosPessoaisUsuario = devolverDadosPessoaisUsuario;
        this.listaTodosUsuariosService = listaTodosUsuariosService;
        this.criarChatService = criarChatService;
        this.devolverMensagensChatService = devolverMensagensChatService;
    }

    @PutMapping("/updatePng")
    public ResponseEntity<AtualizarFotoResponse> atualizarFoto(@RequestBody AtualizarFotoRequest request) throws IOException, InterruptedException {
        return atualizarImagemUsuarioService.atualizarImagemUsuario(request);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<UserUpdatePasswordResponse> userUpdatePassword(@RequestBody UserUpdatePasswordRequest request) {
        return userUpdatePasswordService.updatePassword(request);
    }

    @GetMapping("/devolverDados/{username}")
    public ResponseEntity<DDPUser> devolverDadosUsuarios(@PathVariable String username) {
        return devolverDadosQualquerUsuario.devolverDadosQualquerUsuario(username);
    }

    @GetMapping("/devolverDados")
    public ResponseEntity<DDPUser> devolverDados() {
        return devolverDadosPessoaisUsuario.devolverDadosPessoais();
    }

    @GetMapping("/devolverAllUsuarios")
    public List<UserSummary> devolverTodosUsuarios() {
        return listaTodosUsuariosService.listarTodosUsuarios();
    }

    @PostMapping("/chat/criarChat")
    public ResponseEntity<CriarChatResponse> criarChat(@RequestBody CriarChatRequest request) {
        return criarChatService.criarChat(request);
    }

    @GetMapping("/chat/{id}/mensagens")
    public List<EntityMessage> devolverMensagensChat(@PathVariable UUID id) {
        return devolverMensagensChatService.devolverMensagensChat(id);
    }
}
