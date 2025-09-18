package br.com.imbazzu.listatelefonicacjs.service;

import br.com.imbazzu.listatelefonicacjs.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secrets;

    @Value("${api.security.token.issuer}")
    private String assinatura;

    private final ZoneOffset offset = ZoneOffset.of("-03:00");

    /**
     * Buscar o token no cabeçalho da requisição
     * @param request RequisiçãoHttp
     * @return token JWT
     */
    public String recuperarToken(HttpServletRequest request) {
        //BUscar o token no cabeçalho authorization
        var token = request.getHeader("Authorization");
        //Verifica se possui algo escrito em "Authorization" e se começa com "Bearer"
        if (token != null && token.startsWith("Bearer ")) {
            //Retorna o token caso sim
            return token.substring(7);
        }
        //Retorna nulo
        return null;
    }

    public String gerarToken(Usuario usuario) {
        var roles = usuario.getAuthorities().stream().map(GrantedAuthority::getAuthority
        ).toList();

        Algorithm algorithm = Algorithm.HMAC256(secrets);

        return JWT.create()
                .withIssuer(assinatura)
                .withSubject(usuario.getLogin())
                .withClaim("roles", roles)
                .withExpiresAt(
                        LocalDateTime.now().plusMinutes(15).toInstant(offset)
                )
                .sign(algorithm);
    }

    public String gerarAttToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(secrets);
        return JWT.create()
                .withIssuer(assinatura)
                .withSubject(usuario.getLogin())
                .withExpiresAt(LocalDateTime.now().plusHours(8).toInstant(offset))
                .sign(algorithm);
    }

    public DecodedJWT validarToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secrets);

        return JWT.require(algorithm)
                .withIssuer(assinatura)
                .build()
                .verify(token);
    }


}
