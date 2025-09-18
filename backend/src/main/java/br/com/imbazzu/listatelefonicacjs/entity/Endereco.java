package br.com.imbazzu.listatelefonicacjs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Endereco {
    @Column(length = 100)
    private String logradouro;

    @Column(length = 10)
    private String numero;

    @Column(length = 30)
    private String bairro;

    @Column(length = 100)
    private String cidade;

    @Pattern(regexp = "[A-Z]{2}", message = "UF inválida")
    @Column(length = 2)
    private String uf;

    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido")
    @Column(length = 10)
    private String cep;
}
