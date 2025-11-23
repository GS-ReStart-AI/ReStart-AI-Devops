package br.com.restartai.restart_ai.dto.usuario;

public class AuthRespostaDTO {

    private String token;
    private UsuarioRespostaDTO usuario;

    public AuthRespostaDTO() {
    }

    public AuthRespostaDTO(String token, UsuarioRespostaDTO usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UsuarioRespostaDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioRespostaDTO usuario) {
        this.usuario = usuario;
    }
}
