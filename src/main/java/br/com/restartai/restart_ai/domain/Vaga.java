package br.com.restartai.restart_ai.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "VAGA")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VAGA_ID")
    private Long id;

    @Column(name = "TITULO", length = 120, nullable = false)
    private String titulo;

    @Column(name = "EMPRESA", length = 120)
    private String empresa;

    @Column(name = "CIDADE", length = 100)
    private String cidade;

    @Column(name = "SENIORIDADE", length = 40)
    private String senioridade;

    @Lob
    @Column(name = "DESCRICAO")
    private String descricao;

    @Lob
    @Column(name = "REQ_MUST")
    private String reqMust;

    @Lob
    @Column(name = "REQ_NICE")
    private String reqNice;

    @Column(name = "ATIVA", length = 1, nullable = false)
    private String ativa;

    @Column(name = "CRIADA_EM", nullable = false, updatable = false)
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
