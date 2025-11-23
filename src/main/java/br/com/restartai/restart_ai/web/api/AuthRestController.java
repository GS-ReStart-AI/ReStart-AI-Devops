package br.com.restartai.restart_ai.web.api;

import br.com.restartai.restart_ai.domain.Usuario;
import br.com.restartai.restart_ai.dto.usuario.AuthRespostaDTO;
import br.com.restartai.restart_ai.dto.usuario.UsuarioLoginDTO;
import br.com.restartai.restart_ai.dto.usuario.UsuarioRespostaDTO;
import br.com.restartai.restart_ai.security.JwtTokenService;
import br.com.restartai.restart_ai.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticação", description = "Operações de autenticação de usuários")
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final UsuarioService usuarioService;
    private final JwtTokenService jwtTokenService;

    public AuthRestController(UsuarioService usuarioService,
                              JwtTokenService jwtTokenService) {
        this.usuarioService = usuarioService;
        this.jwtTokenService = jwtTokenService;
    }

    @Operation(
            summary = "Autenticar usuário",
            description = "Realiza o login do usuário e retorna um token JWT.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autenticação realizada com sucesso",
                            content = @Content(schema = @Schema(implementation = AuthRespostaDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Credenciais inválidas",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthRespostaDTO> login(@Valid @RequestBody UsuarioLoginDTO loginDTO) {
        Usuario usuario = usuarioService.autenticar(loginDTO.getEmail(), loginDTO.getSenha());
        String token = jwtTokenService.generateToken(usuario);
        UsuarioRespostaDTO usuarioRespostaDTO = toRespostaDTO(usuario);
        AuthRespostaDTO resposta = new AuthRespostaDTO(token, usuarioRespostaDTO);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
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
