package br.com.imbazzu.listatelefonicacjs.dto.categoria;

import jakarta.validation.constraints.NotBlank;

public record CategoriaCriarDto (@NotBlank String nome){
}
