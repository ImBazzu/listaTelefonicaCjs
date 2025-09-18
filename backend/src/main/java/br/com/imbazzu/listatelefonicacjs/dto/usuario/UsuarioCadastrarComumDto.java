package br.com.imbazzu.listatelefonicacjs.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record UsuarioCadastrarComumDto(@NotBlank String nome, @NotBlank String senha) {
}
