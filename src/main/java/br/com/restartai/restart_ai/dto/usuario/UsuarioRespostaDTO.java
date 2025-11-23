package br.com.restartai.restart_ai.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Dados de saída do usuário")
public class UsuarioRespostaDTO {

    @Schema(description = "Identificador único do usuário", example = "1")
    private Long id;

    @Schema(description = "Nome completo do usuário", example = "Ana Silva")
    private String nomeCompleto;

    @Schema(description = "CPF do usuário, apenas números", example = "12345678901")
    private String cpf;

    @Schema(description = "Data de nascimento do usuário", example = "1990-05-10")
    private LocalDate dataNascimento;

    @Schema(description = "E-mail do usuário", example = "ana.silva@exemplo.com")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
