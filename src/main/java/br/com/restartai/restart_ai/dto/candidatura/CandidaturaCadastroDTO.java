package br.com.restartai.restart_ai.dto.candidatura;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro ou atualização de candidatura")
public class CandidaturaCadastroDTO {

    @Schema(description = "ID do usuário que está se candidatando", example = "1")
    @NotNull
    private Long usuarioId;

    @Schema(description = "ID da vaga para a qual o usuário está se candidatando", example = "10")
    @NotNull
    private Long vagaId;

    @Schema(description = "Status da candidatura: ENVIADA, SALVA, ENTREVISTA, RECUSADA ou APROVADA", example = "ENVIADA")
    @Pattern(regexp = "ENVIADA|SALVA|ENTREVISTA|RECUSADA|APROVADA")
    private String status;

    @Schema(description = "Score de compatibilidade entre o currículo do usuário e a vaga (0..100)", example = "85")
    @Min(0)
    @Max(100)
    private Integer scoreMatch;

    @Schema(description = "Resumo curto gerado pela IA explicando por que o candidato é um bom fit", example = "Experiência sólida em Java, microserviços e cloud.")
    @Size(max = 400)
    private String whyMe;

    @Schema(description = "URL externa para aplicação (LinkedIn, Indeed, etc.)", example = "https://www.linkedin.com/jobs/view/123456")
    @Size(max = 500)
    private String applyUrl;

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
}
