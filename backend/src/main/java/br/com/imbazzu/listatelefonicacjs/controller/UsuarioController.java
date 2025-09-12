package br.com.imbazzu.listatelefonicacjs.controller;

import br.com.imbazzu.listatelefonicacjs.dto.token.TokenRequest;
import br.com.imbazzu.listatelefonicacjs.dto.token.TokenResponse;
import br.com.imbazzu.listatelefonicacjs.dto.usuario.UsuarioCadastrarComumDto;
import br.com.imbazzu.listatelefonicacjs.service.UsuarioService;
import br.com.imbazzu.listatelefonicacjs.dto.usuario.UsuarioLoginRequest;
import br.com.imbazzu.listatelefonicacjs.dto.usuario.UsuarioCadastrarRequest;
import br.com.imbazzu.listatelefonicacjs.service.TokenService;
import br.com.imbazzu.listatelefonicacjs.entity.Usuario;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class UsuarioController {


    private final UsuarioService service;

    private final AuthenticationManager authManager;

    private final TokenService tokenService;

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid UsuarioLoginRequest dto,
                                               HttpServletResponse response) {
        var usuarioSenha = new UsernamePasswordAuthenticationToken(dto.login(),dto.senha());
        var auth = this.authManager.authenticate(usuarioSenha);

        var usuario = (Usuario) auth.getPrincipal();

        var tokenAcesso = tokenService.gerarToken(usuario);
        var tokenAtualizar = tokenService.gerarAttToken(usuario);
        adicionarTokenAtualizarNoCookie(response,tokenAtualizar);
        return ResponseEntity.ok().body(new TokenResponse(tokenAcesso));
    }

    @PostMapping("/atualizarToken")
    @PermitAll
    public ResponseEntity<TokenResponse> atualizar(
             HttpServletResponse response, @CookieValue("tokenAtualizar") @Valid TokenRequest tokenRequest) {
        var decode = tokenService.validarToken(tokenRequest.refreshToken());
        var usuario = (Usuario) this.service.loadUserByUsername(decode.getSubject());

        var tokenAcesso = tokenService.gerarToken(usuario);
        var tokenAtualizar = tokenService.gerarAttToken(usuario);
        adicionarTokenAtualizarNoCookie(response,tokenAtualizar);

        return ResponseEntity.ok().body(new TokenResponse(tokenAcesso));
    }

    @PostMapping("/registrar")
    @PermitAll
    public ResponseEntity<String> registrar(@RequestBody @Valid UsuarioCadastrarComumDto dto) {

        this.service.salvarUsuario(dto);
        return ResponseEntity.ok().body("Usuario cadastrado com sucesso");
    }

    @PostMapping("/registrarAdm")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> registrarAdm(@RequestBody @Valid UsuarioCadastrarRequest dto) {

        this.service.salvarUsuario(dto);
        return ResponseEntity.ok().body("Usuario cadastrado com sucesso");
    }

    private void adicionarTokenAtualizarNoCookie(HttpServletResponse response,
                                                           String token) {
        var cookie = ResponseCookie.from("tokenAtualizar", token)
                .httpOnly(true)
                .path("/auth/atualizarToken")
                .sameSite("None")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
