package br.com.imbazzu.listatelefonicacjs.dto.categoria;

import jakarta.validation.constraints.NotNull;

public record CategoriaRequestDto(@NotNull Long id) {
}
