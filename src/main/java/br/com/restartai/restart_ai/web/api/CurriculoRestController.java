package br.com.restartai.restart_ai.web.api;

import br.com.restartai.restart_ai.domain.Curriculo;
import br.com.restartai.restart_ai.dto.curriculo.CurriculoCadastroDTO;
import br.com.restartai.restart_ai.dto.curriculo.CurriculoRespostaDTO;
import br.com.restartai.restart_ai.service.AnaliseCurriculoIaService;
import br.com.restartai.restart_ai.service.CurriculoService;
import br.com.restartai.restart_ai.service.CurriculoUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Tag(name = "2. Currículos", description = "CRUD de currículos de usuários")
@RestController
@RequestMapping("/api/curriculos")
public class CurriculoRestController {

    private final CurriculoService curriculoService;
    private final CurriculoUploadService curriculoUploadService;
    private final AnaliseCurriculoIaService analiseCurriculoIaService;

    public CurriculoRestController(CurriculoService curriculoService,
                                   CurriculoUploadService curriculoUploadService,
                                   AnaliseCurriculoIaService analiseCurriculoIaService) {
        this.curriculoService = curriculoService;
        this.curriculoUploadService = curriculoUploadService;
        this.analiseCurriculoIaService = analiseCurriculoIaService;
    }

    @Operation(summary = "Listar currículos (paginado)")
    @GetMapping
    public Page<CurriculoRespostaDTO> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "criadoEm") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("DESC")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return curriculoService.listar(pageable).map(this::toRespostaDTO);
    }

    @Operation(summary = "Buscar currículo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CurriculoRespostaDTO> buscarPorId(@PathVariable Long id) {
        Curriculo curriculo = curriculoService.buscarPorId(id);
        return ResponseEntity.ok(toRespostaDTO(curriculo));
    }

    @Operation(summary = "Criar novo currículo (JSON)")
    @PostMapping
    public ResponseEntity<CurriculoRespostaDTO> criar(@Valid @RequestBody CurriculoCadastroDTO dto) {
        Curriculo curriculo = curriculoService.criar(dto);
        CurriculoRespostaDTO respostaDTO = toRespostaDTO(curriculo);

        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(respostaDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(respostaDTO);
    }

    @Operation(summary = "Atualizar currículo")
    @PutMapping("/{id}")
    public ResponseEntity<CurriculoRespostaDTO> atualizar(@PathVariable Long id,
                                                          @Valid @RequestBody CurriculoCadastroDTO dto) {
        Curriculo curriculo = curriculoService.atualizar(id, dto);
        return ResponseEntity.ok(toRespostaDTO(curriculo));
    }

    @Operation(summary = "Excluir currículo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        curriculoService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Upload de currículo em PDF")
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<CurriculoRespostaDTO> uploadPdf(
            @RequestParam Long usuarioId,
            @RequestPart("arquivo") MultipartFile arquivo) {
        Curriculo curriculo = curriculoUploadService.uploadPdf(usuarioId, arquivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(toRespostaDTO(curriculo));
    }

    @Operation(summary = "Analisar currículo via IA")
    @PostMapping("/{id}/analisar")
    public ResponseEntity<String> analisar(@PathVariable Long id,
                                           @RequestParam(name = "lang", required = false) String lang) {
        String analise = analiseCurriculoIaService.analisarCurriculo(id, lang);
        return ResponseEntity.ok(analise);
    }

    private CurriculoRespostaDTO toRespostaDTO(Curriculo curriculo) {
        CurriculoRespostaDTO dto = new CurriculoRespostaDTO();
        dto.setId(curriculo.getId());
        dto.setUsuarioId(curriculo.getUsuario().getId());
        dto.setTipo(curriculo.getTipo());
        dto.setNomeArquivo(curriculo.getNomeArquivo());
        dto.setTamanhoBytes(curriculo.getTamanhoBytes());
        dto.setArquivoUrl(curriculo.getArquivoUrl());
        dto.setTexto(curriculo.getTexto());
        dto.setStatus(curriculo.getStatus());
        dto.setAnalisadoEm(curriculo.getAnalisadoEm());
        dto.setCriadoEm(curriculo.getCriadoEm());
        return dto;
    }
}
