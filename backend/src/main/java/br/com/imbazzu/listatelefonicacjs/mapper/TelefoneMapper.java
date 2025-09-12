package br.com.imbazzu.listatelefonicacjs.mapper;

import br.com.imbazzu.listatelefonicacjs.dto.telefone.TelefoneDto;
import br.com.imbazzu.listatelefonicacjs.entity.Telefone;

import java.util.Set;
import java.util.stream.Collectors;


public class TelefoneMapper {

    public static Set<Telefone> toEntity(Set<TelefoneDto> telefoneDto) {
        return telefoneDto.stream().map(
                dto-> new Telefone(dto.numero(),dto.principal())).collect(Collectors.toSet());
    }

    public static Set<TelefoneDto> toDto(Set<Telefone> telefones) {
        return telefones.stream().map(tel->new TelefoneDto(tel.getNumero(),tel.isPrincipal())).collect(Collectors.toSet());
    }
}
