package br.com.imbazzu.listatelefonicacjs.dto.erro;

import java.time.LocalDateTime;

public record ErroDto(LocalDateTime dataHora,
                      int status, String erro, String mensagem, String caminho) {
}
