package br.com.imbazzu.listatelefonicacjs.dto.empresa;

import br.com.imbazzu.listatelefonicacjs.dto.categoria.CategoriaRequestDto;
import br.com.imbazzu.listatelefonicacjs.dto.endereco.EnderecoResponse;
import br.com.imbazzu.listatelefonicacjs.dto.telefone.TelefoneDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record EmpresaEditarDto(
        @NotBlank(message = "Id é obrigatorio")
        Long id,
        @NotBlank(message = "Nome é Obrigatorio")
        String nome,

        Set<CategoriaRequestDto> categoriasIds,

        EnderecoResponse endereco,

        @NotNull(message = "Telefone é obrigatorio")
        Set<TelefoneDto> telefones,

        String descricao) {
}
