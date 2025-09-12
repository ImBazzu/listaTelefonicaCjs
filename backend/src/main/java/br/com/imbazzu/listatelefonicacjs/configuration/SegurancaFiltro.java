package br.com.imbazzu.listatelefonicacjs.configuration;

import br.com.imbazzu.listatelefonicacjs.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SegurancaFiltro extends OncePerRequestFilter {

    private final TokenService tokenService;

    /**
     * Logica para autenticação do Token e passagem para o Spring Security do usuario
     * autenticado
     *
     * @param request requisição
     * @param response retorno
     * @param filterChain Corrente do filtro de segurança
     * @throws ServletException -exceção
     * @throws IOException -
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //Recupera do token da requisição
        var token = tokenService.recuperarToken(request);
        //Verificar se o token é vazio
        if (token != null) {
            //Valida o Token
            var decodedJWT = tokenService.validarToken(token);
            //Busca o usuario no Token
            var login = decodedJWT.getSubject();
            //Pega as permissão do usuario
            var roles = decodedJWT.getClaim("roles").asList(String.class);
            //Converte as permissões em autoridades
            var autoridades = roles.stream().map(SimpleGrantedAuthority::new).toList();
            //Gera um token autenticado com o nome do usuario e as autoridades
            var authentication = new UsernamePasswordAuthenticationToken(login, null, autoridades);
            //Informa ao Spring Security que o usuario está autenticado
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        //Chama o proximo Filtro
        filterChain.doFilter(request, response);
    }

}
