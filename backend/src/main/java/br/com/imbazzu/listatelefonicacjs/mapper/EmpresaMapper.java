package br.com.imbazzu.listatelefonicacjs.mapper;

import br.com.imbazzu.listatelefonicacjs.dto.categoria.CategoriaResponseDto;
import br.com.imbazzu.listatelefonicacjs.dto.empresa.EmpresaEditarDto;
import br.com.imbazzu.listatelefonicacjs.dto.empresa.EmpresaRequestDto;
import br.com.imbazzu.listatelefonicacjs.dto.empresa.EmpresaResponseDto;
import br.com.imbazzu.listatelefonicacjs.entity.Empresa;
import br.com.imbazzu.listatelefonicacjs.entity.Telefone;

import java.util.Optional;

public class EmpresaMapper {

    public static Empresa toEntity(EmpresaRequestDto dto) {
        if(dto==null) return null;
        var telefones = TelefoneMapper.toEntity(dto.telefones());

        var empresa = new Empresa(dto.nome(), telefones);

        if(dto.descricao()!=null) empresa.setDescricao(dto.descricao());

        var categorias = CategoriaMapper.toEntity(dto.categoriasIds());
        empresa.setCategorias(categorias);



        var endereco = EnderecoMapper.toEntity(dto.endereco());
        empresa.setEndereco(endereco);


        return empresa;
    }

    public static EmpresaResponseDto toResponse(Empresa empresa) {
        var categoria = CategoriaMapper.responseDto(empresa.getCategorias());
        var telefone = TelefoneMapper.toDto(empresa.getTelefones());
        var endereco = EnderecoMapper.toDto(empresa.getEndereco());

        return new EmpresaResponseDto(empresa.getId(),
                empresa.getNome(),
                categoria,
                endereco,
                telefone,
                empresa.getDescricao());
    }
}
