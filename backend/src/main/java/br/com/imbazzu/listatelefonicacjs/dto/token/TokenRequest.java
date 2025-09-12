package br.com.imbazzu.listatelefonicacjs.dto.token;


import jakarta.validation.constraints.NotNull;

public record TokenRequest(@NotNull String refreshToken) {
}
