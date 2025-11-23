package br.com.restartai.restart_ai.dto.curriculo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Dados de saída de currículo")
public class CurriculoRespostaDTO {

    @Schema(description = "ID do currículo", example = "1")
    private Long id;

    @Schema(description = "ID do usuário dono do currículo", example = "1")
    private Long usuarioId;

    @Schema(description = "Tipo do currículo", example = "PDF")
    private String tipo;

    @Schema(description = "Nome do arquivo enviado", example = "curriculo-ana.pdf")
    private String nomeArquivo;

    @Schema(description = "Tamanho do arquivo em bytes", example = "102400")
    private Long tamanhoBytes;

    @Schema(description = "URL do arquivo em storage externo")
    private String arquivoUrl;

    @Schema(description = "Texto do currículo")
    private String texto;

    @Schema(description = "Status do processamento", example = "ENVIADO")
    private String status;

    @Schema(description = "Data e hora em que foi analisado pela IA")
    private LocalDateTime analisadoEm;

    @Schema(description = "Data e hora de criação do registro")
    private LocalDateTime criadoEm;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public Long getTamanhoBytes() {
        return tamanhoBytes;
    }

    public void setTamanhoBytes(Long tamanhoBytes) {
        this.tamanhoBytes = tamanhoBytes;
    }

    public String getArquivoUrl() {
        return arquivoUrl;
    }

    public void setArquivoUrl(String arquivoUrl) {
        this.arquivoUrl = arquivoUrl;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getAnalisadoEm() {
        return analisadoEm;
    }

    public void setAnalisadoEm(LocalDateTime analisadoEm) {
        this.analisadoEm = analisadoEm;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
