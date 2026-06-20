package ProjetoPhelipe.ChatPrivado.service;

import ProjetoPhelipe.ChatPrivado.dto.UserSummary;
import ProjetoPhelipe.ChatPrivado.repository.RepositoryUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListaTodosUsuariosService {

    private final RepositoryUser repositoryUser;

    public ListaTodosUsuariosService(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    public List<UserSummary> listarTodosUsuarios() {
        return repositoryUser.
                findAll().
                stream().
                map(entity -> new UserSummary(entity.getUsername(),entity.getPerfilImagem())).toList();
    }
}
