package br.com.restartai.restart_ai.web.api;

import br.com.restartai.restart_ai.domain.Usuario;
import br.com.restartai.restart_ai.dto.usuario.UsuarioRespostaDTO;
import br.com.restartai.restart_ai.security.CurrentUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthMeRestController {

    private final CurrentUsuarioService currentUsuarioService;

    public AuthMeRestController(CurrentUsuarioService currentUsuarioService) {
        this.currentUsuarioService = currentUsuarioService;
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioRespostaDTO> me() {
        Usuario usuario = currentUsuarioService.getUsuarioAutenticado();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UsuarioRespostaDTO dto = new UsuarioRespostaDTO();
        dto.setId(usuario.getId());
        dto.setNomeCompleto(usuario.getNomeCompleto());
        dto.setCpf(usuario.getCpf());
        dto.setDataNascimento(usuario.getDataNascimento());
        dto.setEmail(usuario.getEmail());
        return ResponseEntity.ok(dto);
    }
}
