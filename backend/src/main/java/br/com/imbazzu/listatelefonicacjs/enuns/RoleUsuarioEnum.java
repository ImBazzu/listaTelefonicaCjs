package br.com.imbazzu.listatelefonicacjs.enuns;

public enum RoleUsuarioEnum {

    ADMIN("ADMIN"),
    USUARIO("USER");

    private String role;

    RoleUsuarioEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
