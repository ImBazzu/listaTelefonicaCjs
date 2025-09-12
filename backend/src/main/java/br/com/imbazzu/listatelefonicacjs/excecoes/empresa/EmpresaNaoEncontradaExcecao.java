package br.com.imbazzu.listatelefonicacjs.excecoes.empresa;

public class EmpresaNaoEncontradaExcecao extends RuntimeException {
    public EmpresaNaoEncontradaExcecao(String message) {
        super(message);
    }
}
