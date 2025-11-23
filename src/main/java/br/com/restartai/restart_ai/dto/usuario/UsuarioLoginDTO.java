package br.com.restartai.restart_ai.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para autenticação do usuário")
public class UsuarioLoginDTO {

    @Schema(description = "E-mail de acesso do usuário", example = "ana.silva@exemplo.com")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "Senha de acesso do usuário", example = "SenhaForte123!")
    @NotBlank
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
