package br.com.restartai.restart_ai.web.api;

import br.com.restartai.restart_ai.domain.Usuario;
import br.com.restartai.restart_ai.dto.usuario.UsuarioCadastroDTO;
import br.com.restartai.restart_ai.dto.usuario.UsuarioRespostaDTO;
import br.com.restartai.restart_ai.service.UsuarioService;
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
import br.com.restartai.restart_ai.dto.usuario.UsuarioPerfilAtualizacaoDTO;


@Tag(name = "1. Usuários", description = "Operações de CRUD de usuários do Restart.Ai")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    private final UsuarioService usuarioService;

    public UsuarioRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Listar usuários (paginado)")
    @GetMapping
    public Page<UsuarioRespostaDTO> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort) {

        String[] sortParts = sort.split(",");
        String sortField = sortParts[0];
        String sortDirection = sortParts.length > 1 ? sortParts[1] : "asc";

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        return usuarioService.listar(pageable).map(this::toRespostaDTO);
    }

    @Operation(summary = "Buscar usuário por ID")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRespostaDTO> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(toRespostaDTO(usuario));
    }

    @Operation(summary = "Cadastrar novo usuário")
    @PostMapping
    public ResponseEntity<UsuarioRespostaDTO> registrar(@Valid @RequestBody UsuarioCadastroDTO dto) {
        Usuario usuario = usuarioService.registrar(dto);
        UsuarioRespostaDTO resposta = toRespostaDTO(usuario);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resposta.getId())
                .toUri();
        return ResponseEntity.created(uri).body(resposta);
    }

    @Operation(summary = "Atualizar usuário existente")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRespostaDTO> atualizar(@PathVariable Long id,
                                                        @Valid @RequestBody UsuarioCadastroDTO dto) {
        Usuario usuario = usuarioService.atualizar(id, dto);
        return ResponseEntity.ok(toRespostaDTO(usuario));
    }

    @Operation(summary = "Atualizar dados de perfil do usuário (sem alterar senha)")
    @PutMapping("/{id}/perfil")
    public ResponseEntity<UsuarioRespostaDTO> atualizarPerfil(@PathVariable Long id,
                                                              @Valid @RequestBody UsuarioPerfilAtualizacaoDTO dto) {
        Usuario usuario = usuarioService.atualizarPerfil(id, dto);
        return ResponseEntity.ok(toRespostaDTO(usuario));
    }


    @Operation(summary = "Excluir usuário")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private UsuarioRespostaDTO toRespostaDTO(Usuario usuario) {
        UsuarioRespostaDTO dto = new UsuarioRespostaDTO();
        dto.setId(usuario.getId());
        dto.setNomeCompleto(usuario.getNomeCompleto());
        dto.setCpf(usuario.getCpf());
        dto.setDataNascimento(usuario.getDataNascimento());
        dto.setEmail(usuario.getEmail());
        return dto;
    }
}
