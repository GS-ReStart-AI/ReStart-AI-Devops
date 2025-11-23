package br.com.restartai.restart_ai.dto.vaga;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Dados de saída de vaga")
public class VagaRespostaDTO {

    @Schema(description = "ID da vaga", example = "1")
    private Long id;

    @Schema(description = "Título da vaga", example = "Desenvolvedor Java Pleno")
    private String titulo;

    @Schema(description = "Nome da empresa", example = "Restart.Ai")
    private String empresa;

    @Schema(description = "Cidade da vaga", example = "São Paulo")
    private String cidade;

    @Schema(description = "Senioridade da vaga", example = "PLENO")
    private String senioridade;

    @Schema(description = "Descrição da vaga")
    private String descricao;

    @Schema(description = "Requisitos obrigatórios em texto ou JSON")
    private String reqMust;

    @Schema(description = "Requisitos desejáveis em texto ou JSON")
    private String reqNice;

    @Schema(description = "Se a vaga está ativa (S ou N)", example = "S")
    private String ativa;

    @Schema(description = "Data e hora de criação da vaga")
    private LocalDateTime criadaEm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCriadaEm() {
        return criadaEm;
    }

    public void setCriadaEm(LocalDateTime criadaEm) {
        this.criadaEm = criadaEm;
    }
}
