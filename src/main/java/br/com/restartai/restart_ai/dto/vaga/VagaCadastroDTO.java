package br.com.restartai.restart_ai.dto.vaga;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro ou atualização de vaga")
public class VagaCadastroDTO {

    @Schema(description = "Título da vaga", example = "Desenvolvedor Java Pleno")
    @NotBlank
    @Size(max = 120)
    private String titulo;

    @Schema(description = "Nome da empresa", example = "Restart.Ai")
    @Size(max = 120)
    private String empresa;

    @Schema(description = "Cidade da vaga", example = "São Paulo")
    @Size(max = 100)
    private String cidade;

    @Schema(description = "Senioridade da vaga", example = "PLENO")
    @Size(max = 40)
    private String senioridade;

    @Schema(description = "Descrição da vaga")
    private String descricao;

    @Schema(description = "Requisitos obrigatórios em texto ou JSON")
    private String reqMust;

    @Schema(description = "Requisitos desejáveis em texto ou JSON")
    private String reqNice;

    @Schema(description = "Se a vaga está ativa (S ou N)", example = "S")
    @NotBlank
    @Size(min = 1, max = 1)
    private String ativa;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getSenioridade() {
        return senioridade;
    }

    public void setSenioridade(String senioridade) {
        this.senioridade = senioridade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getReqMust() {
        return reqMust;
    }

    public void setReqMust(String reqMust) {
        this.reqMust = reqMust;
    }

    public String getReqNice() {
        return reqNice;
    }

    public void setReqNice(String reqNice) {
        this.reqNice = reqNice;
    }

    public String getAtiva() {
        return ativa;
    }

    public void setAtiva(String ativa) {
        this.ativa = ativa;
    }
}
