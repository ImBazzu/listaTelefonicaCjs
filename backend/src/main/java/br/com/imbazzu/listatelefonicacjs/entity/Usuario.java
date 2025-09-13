package br.com.imbazzu.listatelefonicacjs.entity;

import br.com.imbazzu.listatelefonicacjs.enuns.RoleUsuarioEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Table(name = "usuarios")
@Entity(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String senha;

    @Enumerated(EnumType.STRING)
    private RoleUsuarioEnum role;

    public Usuario(String login, String senha, String role) {
        this.login = login;
        this.senha = senha;
        this.role = RoleUsuarioEnum.valueOf(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + RoleUsuarioEnum.USUARIO.getRole()));

        if (this.role == RoleUsuarioEnum.ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + RoleUsuarioEnum.ADMIN.getRole()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }
}
