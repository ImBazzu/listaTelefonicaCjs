package br.com.imbazzu.listatelefonicacjs.excecoes;

public class UsuarioErro extends RuntimeException {
    public UsuarioErro(String message) {
        super(message);
    }
}
