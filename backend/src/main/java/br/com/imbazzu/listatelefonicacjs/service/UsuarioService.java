package br.com.imbazzu.listatelefonicacjs.service;

import br.com.imbazzu.listatelefonicacjs.dto.usuario.UsuarioCadastrarComumDto;
import br.com.imbazzu.listatelefonicacjs.excecoes.UsuarioErro;
import br.com.imbazzu.listatelefonicacjs.dto.usuario.UsuarioCadastrarRequest;
import br.com.imbazzu.listatelefonicacjs.entity.Usuario;
import br.com.imbazzu.listatelefonicacjs.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repo;

    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repo.findByLogin(username);
    }

    public boolean verificarUsuario(String usuario) {
        return this.repo.existsByLogin(usuario);
    }

    public Usuario salvarUsuario(UsuarioCadastrarRequest dto) {

        if(verificarUsuario(dto.login())){
            throw new UsuarioErro("Usuario ja cadastrado");
        }

        String senhaCriptografada = encoder.encode(dto.senha());

        var usuario = new Usuario(dto.login(),senhaCriptografada, dto.role());

        return this.repo.save(usuario);
    }

    public Usuario salvarUsuario(UsuarioCadastrarComumDto dto) {
        var usuario = new UsuarioCadastrarRequest(dto.nome(), dto.senha(), "USUARIO");
        return salvarUsuario(usuario);
    }

}
