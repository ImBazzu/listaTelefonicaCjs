package br.com.imbazzu.listatelefonicacjs.mapper;

import br.com.imbazzu.listatelefonicacjs.dto.PaginacaoResponse;
import br.com.imbazzu.listatelefonicacjs.dto.categoria.CategoriaCriarDto;
import br.com.imbazzu.listatelefonicacjs.dto.categoria.CategoriaRequestDto;
import br.com.imbazzu.listatelefonicacjs.dto.categoria.CategoriaResponseDto;
import br.com.imbazzu.listatelefonicacjs.entity.Categoria;
import org.springframework.data.domain.Page;

import java.util.Set;
import java.util.stream.Collectors;

public class CategoriaMapper {

    public static Set<Categoria> toEntity(Set<CategoriaRequestDto> categoriasIDs) {
        if(categoriasIDs==null) return Set.of();

        return categoriasIDs.stream().map(CategoriaMapper::toEntity).collect(Collectors.toSet());
    }

    public static Categoria toEntity(CategoriaRequestDto categoriaDTO) {
        var categoria = new Categoria();
        categoria.setId(categoriaDTO.id());
        return categoria;
    }

    public static Categoria toEntity(CategoriaCriarDto dto) {
        var categoria = new Categoria();
        categoria.setNome(dto.nome());
        return categoria;
    }

    public static Set<CategoriaResponseDto> responseDto(Set<Categoria> categoria){
        return categoria.stream().map(CategoriaMapper::responseDto).collect(Collectors.toSet());
    }

    public static CategoriaResponseDto responseDto(Categoria categoria) {
        return new CategoriaResponseDto(categoria.getId(),categoria.getNome());
    }

    public static PaginacaoResponse<CategoriaResponseDto> responseDto(Page<Categoria> page) {
        var conteudo = page.getContent().stream().map(CategoriaMapper::responseDto).toList();
        return new PaginacaoResponse<CategoriaResponseDto>(conteudo,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious());
    }
}
