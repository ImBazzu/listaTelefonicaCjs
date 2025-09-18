package br.com.imbazzu.listatelefonicacjs.mapper;

import br.com.imbazzu.listatelefonicacjs.dto.PaginacaoResponse;
import br.com.imbazzu.listatelefonicacjs.dto.empresa.EmpresaResponseDto;
import br.com.imbazzu.listatelefonicacjs.entity.Empresa;
import org.springframework.data.domain.Page;

public class PaginacaoMapper {

    public static PaginacaoResponse<EmpresaResponseDto> toDto(Page<Empresa> page) {
        var empresaRetorno = page.getContent().stream().map(EmpresaMapper::toResponse).toList();
        return new PaginacaoResponse<>(
                empresaRetorno,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}
