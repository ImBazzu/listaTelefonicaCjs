package br.com.imbazzu.listatelefonicacjs.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@RequiredArgsConstructor
public class SegurancaConfig {

    private final SegurancaFiltro filter;

    /**
     * Filtro do segurança
     *
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable) //Ao desabilitar o csrf permite que a api receba requisições de qualquer ip
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Não salva as sessões dos usuarios
                .authorizeHttpRequests(auth->
                        auth.anyRequest().permitAll()) //Primeiramente permite todos os acessos, pois as permissões são feitas via anotação
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class) //adiciona o filtro JWT costumizado antes do filtro de autenticação
                .build();
    }

    /**
     * Codificador da senha
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration http) throws Exception {
        return http.getAuthenticationManager();
    }
}
