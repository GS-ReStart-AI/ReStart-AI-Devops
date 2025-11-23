package br.com.restartai.restart_ai.dto.curriculo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro ou atualização de currículo")
public class CurriculoCadastroDTO {

    @Schema(description = "ID do usuário dono do currículo", example = "1")
    @NotNull
    private Long usuarioId;

    @Schema(description = "Tipo do currículo: PDF ou TEXTO", example = "PDF")
    @NotBlank
    @Pattern(regexp = "PDF|TEXTO")
    private String tipo;

    @Schema(description = "Nome do arquivo enviado", example = "curriculo-ana.pdf")
    @Size(max = 200)
    private String nomeArquivo;

    @Schema(description = "Tamanho do arquivo em bytes", example = "102400")
    @PositiveOrZero
    private Long tamanhoBytes;

    @Schema(description = "URL do arquivo em storage externo", example = "https://storage.exemplo.com/cv/123.pdf")
    @Size(max = 500)
    private String arquivoUrl;

    @Schema(description = "Texto do currículo, quando enviado como texto puro ou extraído do PDF")
    private String texto;

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
}
