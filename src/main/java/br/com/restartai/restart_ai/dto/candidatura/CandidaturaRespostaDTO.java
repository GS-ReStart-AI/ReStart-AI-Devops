package br.com.restartai.restart_ai.dto.candidatura;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Dados de saída de candidatura")
public class CandidaturaRespostaDTO {

    @Schema(description = "ID da candidatura", example = "1")
    private Long id;

    @Schema(description = "ID do usuário que se candidatou", example = "1")
    private Long usuarioId;

    @Schema(description = "ID da vaga para a qual o usuário se candidatou", example = "10")
    private Long vagaId;

    @Schema(description = "Status da candidatura", example = "ENVIADA")
    private String status;

    @Schema(description = "Score de compatibilidade entre o currículo do usuário e a vaga (0..100)", example = "85")
    private Integer scoreMatch;

    @Schema(description = "Resumo curto explicando por que o candidato é um bom fit")
    private String whyMe;

    @Schema(description = "URL externa para aplicação (LinkedIn, Indeed, etc.)")
    private String applyUrl;

    @Schema(description = "Data e hora em que o usuário aplicou para a vaga")
    private LocalDateTime aplicadaEm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getVagaId() {
        return vagaId;
    }

    public void setVagaId(Long vagaId) {
        this.vagaId = vagaId;
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

    public LocalDateTime getAplicadaEm() {
        return aplicadaEm;
    }

    public void setAplicadaEm(LocalDateTime aplicadaEm) {
        this.aplicadaEm = aplicadaEm;
    }
}
