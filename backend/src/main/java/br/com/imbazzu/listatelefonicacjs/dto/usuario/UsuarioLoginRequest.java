package br.com.imbazzu.listatelefonicacjs.dto.usuario;


import jakarta.validation.constraints.NotNull;

public record UsuarioLoginRequest(@NotNull String login, @NotNull String senha) {
}
