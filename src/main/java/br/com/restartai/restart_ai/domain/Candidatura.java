package br.com.restartai.restart_ai.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CANDIDATURA",
        uniqueConstraints = {
                @UniqueConstraint(name = "UQ_CAND_USR_VAGA", columnNames = {"USUARIO_ID", "VAGA_ID"})
        })
public class Candidatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CANDIDATURA_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VAGA_ID", nullable = false)
    private Vaga vaga;

    @Column(name = "APLICADA_EM", nullable = false)
    private LocalDateTime aplicadaEm;

    @Column(name = "STATUS", length = 20, nullable = false)
    private String status;

    @Column(name = "SCORE_MATCH")
    private Integer scoreMatch;

    @Column(name = "WHY_ME", length = 400)
    private String whyMe;

    @Column(name = "APPLY_URL", length = 500)
    private String applyUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public LocalDateTime getAplicadaEm() {
        return aplicadaEm;
    }

    public void setAplicadaEm(LocalDateTime aplicadaEm) {
        this.aplicadaEm = aplicadaEm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getScoreMatch() {
        return scoreMatch;
    }

    public void setScoreMatch(Integer scoreMatch) {
        this.scoreMatch = scoreMatch;
    }

    public String getWhyMe() {
        return whyMe;
    }

    public void setWhyMe(String whyMe) {
        this.whyMe = whyMe;
    }

    public String getApplyUrl() {
        return applyUrl;
    }

    public void setApplyUrl(String applyUrl) {
        this.applyUrl = applyUrl;
    }
}
