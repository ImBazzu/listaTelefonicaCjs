package br.com.imbazzu.listatelefonicacjs.repository;

import br.com.imbazzu.listatelefonicacjs.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByLogin(String usuario);

    boolean existsByLogin(String usuario);
}
