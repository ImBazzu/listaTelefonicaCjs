package br.com.imbazzu.listatelefonicacjs.controller;

import br.com.imbazzu.listatelefonicacjs.dto.erro.ErroDto;
import br.com.imbazzu.listatelefonicacjs.excecoes.UsuarioErro;
import br.com.imbazzu.listatelefonicacjs.excecoes.categoria.CategoriaJaCadastradaExcecao;
import br.com.imbazzu.listatelefonicacjs.excecoes.categoria.CategoriaNaoEncontradaExcecao;
import br.com.imbazzu.listatelefonicacjs.excecoes.categoria.CategoriaListaVaziaExcecao;
import br.com.imbazzu.listatelefonicacjs.excecoes.empresa.EmpresaJaExisteExcecao;
import br.com.imbazzu.listatelefonicacjs.excecoes.empresa.EmpresaNaoEncontradaExcecao;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalErroController {


    @ExceptionHandler(UsuarioErro.class)
    public ResponseEntity<ErroDto> usuarioExiste(UsuarioErro ex, HttpServletRequest request) {
        var http = HttpStatus.FORBIDDEN;
        var dto = new ErroDto(LocalDateTime.now(),http.value(),http.getReasonPhrase(),
                ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(http).body(dto);
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<ErroDto> erroJWT(JWTCreationException ex, HttpServletRequest request) {
        var http = HttpStatus.INTERNAL_SERVER_ERROR;
        String msg = "Erro ao tentar gerar token";
        var dto = new ErroDto(
                LocalDateTime.now(),http.value(), http.getReasonPhrase(), msg, request.getRequestURI()
        );
        return ResponseEntity.status(http).body(dto);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ErroDto> jWTValidarErro(JWTVerificationException ex, HttpServletRequest request) {
        var http = HttpStatus.UNAUTHORIZED;
        String msg = "Erro ao tentar validar token";
        var dto = new ErroDto(LocalDateTime.now(),
                http.value(), http.getReasonPhrase(), msg, request.getRequestURI());
        return ResponseEntity.status(http).body(dto);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErroDto> autenticacaoExcecao(AuthenticationException ex, HttpServletRequest request) {
        var http = HttpStatus.UNAUTHORIZED;
        String msg = "Senha ou Login incorreta";
        var dto = new ErroDto(LocalDateTime.now(),
                http.value(), http.getReasonPhrase(), msg, request.getRequestURI());
        return ResponseEntity.status(http).body(dto);
    }

    @ExceptionHandler(EmpresaNaoEncontradaExcecao.class)
    public ResponseEntity<ErroDto> entidadeNaoEncontradaExcecao(EmpresaNaoEncontradaExcecao ex, HttpServletRequest request) {
        var http = HttpStatus.NOT_FOUND;
        var dto = new ErroDto(LocalDateTime.now(), http.value(), http.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(http).body(dto);
    }

    @ExceptionHandler(EmpresaJaExisteExcecao.class)
    public ResponseEntity<ErroDto> empresaExiste(EmpresaJaExisteExcecao ex, HttpServletRequest request) {
        var http = HttpStatus.BAD_REQUEST;
        var dto = new ErroDto(LocalDateTime.now(), http.value(), http.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(http).body(dto);
    }

    @ExceptionHandler(CategoriaListaVaziaExcecao.class)
    public ResponseEntity<ErroDto>categoriasIdsVazio(CategoriaListaVaziaExcecao ex, HttpServletRequest request){
        var http = HttpStatus.BAD_REQUEST;
        var dto = new ErroDto(LocalDateTime.now(), http.value(),http.getReasonPhrase() ,ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(http).body(dto);
    }

    @ExceptionHandler(CategoriaNaoEncontradaExcecao.class)
    public ResponseEntity<ErroDto> categoriasInexistentes(CategoriaNaoEncontradaExcecao ex, HttpServletRequest request){
        var http = HttpStatus.BAD_REQUEST;
        var dto = new ErroDto(LocalDateTime.now(), http.value(),http.getReasonPhrase() ,ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(http).body(dto);
    }

    @ExceptionHandler(CategoriaJaCadastradaExcecao.class)
    public ResponseEntity<ErroDto> categoriaJaCadastrada(CategoriaJaCadastradaExcecao ex, HttpServletRequest request){
        var http = HttpStatus.BAD_REQUEST;
        var dto = new ErroDto(LocalDateTime.now(), http.value(),http.getReasonPhrase() ,ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(http).body(dto);
    }
}
