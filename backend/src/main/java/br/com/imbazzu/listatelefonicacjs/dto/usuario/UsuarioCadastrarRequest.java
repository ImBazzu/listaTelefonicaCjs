package br.com.imbazzu.listatelefonicacjs.dto.usuario;

import jakarta.validation.constraints.NotNull;

public record UsuarioCadastrarRequest(
        @NotNull
        String login,
        @NotNull
        String senha,

        @NotNull
        String role
) {
}
