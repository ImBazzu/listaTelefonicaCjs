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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // habilita CORS com configuração
                .csrf(AbstractHttpConfigurer::disable) //Ao desabilitar o csrf permite que a api receba requisições de qualquer ip
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
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Defina os domínios permitidos
        configuration.setAllowedOriginPatterns(List.of("*"));
        // Métodos permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Headers permitidos, incluindo Authorization para JWT
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        // Permitir envio de cookies e credenciais
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // aplica para todas as rotas
        return source;
    }
}
