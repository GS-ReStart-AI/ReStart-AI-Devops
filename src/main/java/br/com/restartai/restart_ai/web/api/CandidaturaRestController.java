package br.com.restartai.restart_ai.web.api;

import br.com.restartai.restart_ai.domain.Candidatura;
import br.com.restartai.restart_ai.dto.candidatura.CandidaturaCadastroDTO;
import br.com.restartai.restart_ai.dto.candidatura.CandidaturaRespostaDTO;
import br.com.restartai.restart_ai.service.CandidaturaService;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Tag(name = "4. Candidaturas", description = "CRUD de candidaturas de usuários às vagas")
@RestController
@RequestMapping("/api/candidaturas")
public class CandidaturaRestController {

    private final CandidaturaService candidaturaService;

    public CandidaturaRestController(CandidaturaService candidaturaService) {
        this.candidaturaService = candidaturaService;
    }

    @Operation(summary = "Listar candidaturas (paginado)")
    @GetMapping
    public Page<CandidaturaRespostaDTO> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort) {

        String[] sortParts = sort.split(",");
        String sortField = sortParts[0];
        String sortDirection = sortParts.length > 1 ? sortParts[1] : "asc";

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        return candidaturaService.listar(pageable).map(this::toRespostaDTO);
    }

    @Operation(summary = "Buscar candidatura por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CandidaturaRespostaDTO> buscarPorId(@PathVariable Long id) {
        Candidatura candidatura = candidaturaService.buscarPorId(id);
        return ResponseEntity.ok(toRespostaDTO(candidatura));
    }

    @Operation(summary = "Cadastrar nova candidatura")
    @PostMapping
    public ResponseEntity<CandidaturaRespostaDTO> criar(@Valid @RequestBody CandidaturaCadastroDTO dto) {
        Candidatura candidatura = candidaturaService.criar(dto);
        CandidaturaRespostaDTO resposta = toRespostaDTO(candidatura);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resposta.getId())
                .toUri();
        return ResponseEntity.created(uri).body(resposta);
    }

    @Operation(summary = "Atualizar candidatura existente")
    @PutMapping("/{id}")
    public ResponseEntity<CandidaturaRespostaDTO> atualizar(@PathVariable Long id,
                                                            @Valid @RequestBody CandidaturaCadastroDTO dto) {
        Candidatura candidatura = candidaturaService.atualizar(id, dto);
        return ResponseEntity.ok(toRespostaDTO(candidatura));
    }

    @Operation(summary = "Excluir candidatura")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        candidaturaService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private CandidaturaRespostaDTO toRespostaDTO(Candidatura candidatura) {
        CandidaturaRespostaDTO dto = new CandidaturaRespostaDTO();
        dto.setId(candidatura.getId());
        dto.setUsuarioId(candidatura.getUsuario().getId());
        dto.setVagaId(candidatura.getVaga().getId());
        dto.setStatus(candidatura.getStatus());
        dto.setScoreMatch(candidatura.getScoreMatch());
        dto.setWhyMe(candidatura.getWhyMe());
        dto.setApplyUrl(candidatura.getApplyUrl());
        dto.setAplicadaEm(candidatura.getAplicadaEm());
        return dto;
    }
}
