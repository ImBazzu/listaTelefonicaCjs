package br.com.imbazzu.listatelefonicacjs.mapper;

import br.com.imbazzu.listatelefonicacjs.dto.endereco.EnderecoResponse;
import br.com.imbazzu.listatelefonicacjs.entity.Endereco;

import java.util.Optional;

public class EnderecoMapper {

    public static Endereco toEntity(EnderecoResponse dto) {
        var endereco = new Endereco();
        if (dto == null) {
            return endereco;
        }
        endereco.setUf(dto.uf());
        endereco.setCidade(dto.cidade());
        endereco.setBairro(dto.bairro());
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setCep(dto.cep());
        return endereco;
    }

    public static EnderecoResponse toDto(Endereco endereco) {
        if(endereco==null) return null;
        return new EnderecoResponse(endereco.getLogradouro(),endereco.getNumero(),endereco.getBairro(),
                endereco.getCidade(),endereco.getUf(),endereco.getCep());
    }
}
