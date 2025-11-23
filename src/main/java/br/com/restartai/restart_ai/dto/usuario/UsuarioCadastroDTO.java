package br.com.restartai.restart_ai.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "Dados para cadastro ou atualização de usuário")
public class UsuarioCadastroDTO {

    @Schema(description = "Nome completo do usuário", example = "Ana Silva")
    @NotBlank
    @Size(max = 120)
    private String nomeCompleto;

    @Schema(description = "CPF do usuário, apenas números", example = "12345678901")
    @NotBlank
    @Pattern(regexp = "^[0-9]{11}$")
    private String cpf;

    @Schema(description = "Data de nascimento do usuário", example = "1990-05-10")
    @Past
    private LocalDate dataNascimento;

    @Schema(description = "E-mail de acesso do usuário", example = "ana.silva@exemplo.com")
    @NotBlank
    @Email
    @Size(max = 150)
    private String email;

    @Schema(description = "Senha de acesso do usuário", example = "SenhaForte123!")
    @NotBlank
    @Size(min = 8, max = 72)
    private String senha;

    @Schema(description = "Confirmação da senha de acesso", example = "SenhaForte123!")
    @NotBlank
    @Size(min = 8, max = 72)
    private String confirmarSenha;

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

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

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }
}
