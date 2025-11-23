package br.com.restartai.restart_ai.service;

import br.com.restartai.restart_ai.domain.Curriculo;
import br.com.restartai.restart_ai.dto.curriculo.CurriculoCadastroDTO;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CurriculoUploadService {

    private final CurriculoService curriculoService;

    public CurriculoUploadService(CurriculoService curriculoService) {
        this.curriculoService = curriculoService;
    }

    public Curriculo uploadPdf(Long usuarioId, MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo PDF obrigatório.");
        }

        String textoExtraido = extrairTextoPdf(arquivo);

        CurriculoCadastroDTO dto = new CurriculoCadastroDTO();
        dto.setUsuarioId(usuarioId);
        dto.setTipo("PDF");
        dto.setNomeArquivo(arquivo.getOriginalFilename());
        dto.setTamanhoBytes(arquivo.getSize());
        dto.setArquivoUrl(null);
        dto.setTexto(textoExtraido);

        return curriculoService.criar(dto);
    }

    private String extrairTextoPdf(MultipartFile arquivo) {
        try (PDDocument document = PDDocument.load(arquivo.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar arquivo PDF", e);
        }
    }
}
