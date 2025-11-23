package br.com.restartai.restart_ai.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "Dados básicos de perfil do usuário (sem senha)")
public class UsuarioPerfilAtualizacaoDTO {

    @Schema(description = "Nome completo do usuário", example = "Ana Silva")
    @NotBlank
    @Size(max = 120)
    private String nomeCompleto;

    @Schema(description = "CPF do usuário, apenas números", example = "12345678901")
    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos.")
    private String cpf;

    @Schema(description = "Data de nascimento do usuário", example = "1990-01-01")
    @Past
    private LocalDate dataNascimento;

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
}
