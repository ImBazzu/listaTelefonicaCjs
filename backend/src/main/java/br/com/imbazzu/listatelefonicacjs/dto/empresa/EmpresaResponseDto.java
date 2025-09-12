package br.com.imbazzu.listatelefonicacjs.dto.empresa;

import br.com.imbazzu.listatelefonicacjs.dto.categoria.CategoriaResponseDto;
import br.com.imbazzu.listatelefonicacjs.dto.endereco.EnderecoResponse;
import br.com.imbazzu.listatelefonicacjs.dto.telefone.TelefoneDto;

import java.util.Set;

public record EmpresaResponseDto(Long id,
                                 String nome,

                                 Set<CategoriaResponseDto> categorias,

                                 EnderecoResponse endereco,

                                 Set<TelefoneDto> telefones,

                                 String descricao) {
}
