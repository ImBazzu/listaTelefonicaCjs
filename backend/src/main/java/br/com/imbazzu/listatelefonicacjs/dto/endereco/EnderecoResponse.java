package br.com.imbazzu.listatelefonicacjs.dto.endereco;

public record EnderecoResponse(String logradouro, String numero, String bairro,
                               String cidade, String uf, String cep) {
}
