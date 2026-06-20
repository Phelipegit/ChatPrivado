package ProjetoPhelipe.ChatPrivado.controller;

import ProjetoPhelipe.ChatPrivado.dto.*;
import ProjetoPhelipe.ChatPrivado.entity.EntityMessage;
import ProjetoPhelipe.ChatPrivado.service.*;
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
    private final DevolverChatsUsuario devolverChatsUsuario;
    private final CriarChatService criarChatService;
    private final DevolverMensagensChatService devolverMensagensChatService;

    public UserController(AtualizarImagemUsuarioService atualizarImagemUsuarioService,
                          UserUpdatePasswordService userUpdatePasswordService,
                          DevolverDadosQualquerUsuario devolverDadosQualquerUsuario ,
                          DevolverDadosPessoaisUsuario devolverDadosPessoaisUsuario,
                          ListaTodosUsuariosService listaTodosUsuariosService,
                          DevolverChatsUsuario devolverChatsUsuario,
                          CriarChatService criarChatService,
                          DevolverMensagensChatService devolverMensagensChatService) {
        this.atualizarImagemUsuarioService = atualizarImagemUsuarioService;
        this.userUpdatePasswordService = userUpdatePasswordService;
        this.devolverDadosQualquerUsuario = devolverDadosQualquerUsuario;
        this.devolverDadosPessoaisUsuario = devolverDadosPessoaisUsuario;
        this.listaTodosUsuariosService = listaTodosUsuariosService;
        this.devolverChatsUsuario = devolverChatsUsuario;
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

    @GetMapping("/devolverAllChats")
    public ResponseEntity<List<DDPChats>> devolverChatsUsuario() {
        return devolverChatsUsuario.devolverChatsUsuario();
    }

    @PostMapping("/chat/criarChat")
    public ResponseEntity<CriarChatResponse> criarChat(@RequestBody CriarChatRequest request) {
        return criarChatService.criarChat(request);
    }

    @GetMapping("/chat/{id}/mensagens")
    public ResponseEntity<List<EntityMessage>> devolverMensagensChat(@PathVariable UUID id) {
        return devolverMensagensChatService.devolverMensagensChat(id);
    }
}
