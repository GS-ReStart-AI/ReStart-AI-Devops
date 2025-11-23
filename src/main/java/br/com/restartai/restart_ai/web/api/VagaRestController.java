package br.com.restartai.restart_ai.web.api;

import br.com.restartai.restart_ai.domain.Vaga;
import br.com.restartai.restart_ai.dto.vaga.VagaCadastroDTO;
import br.com.restartai.restart_ai.dto.vaga.VagaRespostaDTO;
import br.com.restartai.restart_ai.service.VagaService;
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

@Tag(name = "3. Vagas", description = "CRUD de vagas do catálogo Restart.Ai")
@RestController
@RequestMapping("/api/vagas")
public class VagaRestController {

    private final VagaService vagaService;

    public VagaRestController(VagaService vagaService) {
        this.vagaService = vagaService;
    }

    @Operation(summary = "Listar vagas (paginado)")
    @GetMapping
    public Page<VagaRespostaDTO> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort) {

        String[] sortParts = sort.split(",");
        String sortField = sortParts[0];
        String sortDirection = sortParts.length > 1 ? sortParts[1] : "asc";

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        return vagaService.listar(pageable).map(this::toRespostaDTO);
    }

    @Operation(summary = "Buscar vaga por ID")
    @GetMapping("/{id}")
    public ResponseEntity<VagaRespostaDTO> buscarPorId(@PathVariable Long id) {
        Vaga vaga = vagaService.buscarPorId(id);
        return ResponseEntity.ok(toRespostaDTO(vaga));
    }

    @Operation(summary = "Cadastrar nova vaga")
    @PostMapping
    public ResponseEntity<VagaRespostaDTO> criar(@Valid @RequestBody VagaCadastroDTO dto) {
        Vaga vaga = vagaService.criar(dto);
        VagaRespostaDTO resposta = toRespostaDTO(vaga);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resposta.getId())
                .toUri();
        return ResponseEntity.created(uri).body(resposta);
    }

    @Operation(summary = "Atualizar vaga existente")
    @PutMapping("/{id}")
    public ResponseEntity<VagaRespostaDTO> atualizar(@PathVariable Long id,
                                                     @Valid @RequestBody VagaCadastroDTO dto) {
        Vaga vaga = vagaService.atualizar(id, dto);
        return ResponseEntity.ok(toRespostaDTO(vaga));
    }

    @Operation(summary = "Excluir vaga")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        vagaService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private VagaRespostaDTO toRespostaDTO(Vaga vaga) {
        VagaRespostaDTO dto = new VagaRespostaDTO();
        dto.setId(vaga.getId());
        dto.setTitulo(vaga.getTitulo());
        dto.setEmpresa(vaga.getEmpresa());
        dto.setCidade(vaga.getCidade());
        dto.setSenioridade(vaga.getSenioridade());
        dto.setDescricao(vaga.getDescricao());
        dto.setReqMust(vaga.getReqMust());
        dto.setReqNice(vaga.getReqNice());
        dto.setAtiva(vaga.getAtiva());
        dto.setCriadaEm(vaga.getCriadaEm());
        return dto;
    }
}
