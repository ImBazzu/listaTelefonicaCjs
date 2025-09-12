package br.com.imbazzu.listatelefonicacjs.dto;

import java.util.List;

public record PaginacaoResponse<T>(List<T> conteudo,
                                   int page,
                                   int size,
                                   long totalElements,
                                   int totalPages,
                                   boolean hasNext,
                                   boolean hasPrevious) {
}
