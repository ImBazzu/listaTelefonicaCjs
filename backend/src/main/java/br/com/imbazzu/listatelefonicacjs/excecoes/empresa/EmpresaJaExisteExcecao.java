package br.com.imbazzu.listatelefonicacjs.excecoes.empresa;

public class EmpresaJaExisteExcecao extends RuntimeException {
    public EmpresaJaExisteExcecao(String message) {
        super(message);
    }
}
