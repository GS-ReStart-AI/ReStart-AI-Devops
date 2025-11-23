package br.com.restartai.restart_ai.web.view;

import br.com.restartai.restart_ai.domain.Usuario;
import br.com.restartai.restart_ai.dto.usuario.UsuarioCadastroDTO;
import br.com.restartai.restart_ai.dto.usuario.UsuarioLoginDTO;
import br.com.restartai.restart_ai.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios/cadastro")
    public String telaCadastro(@ModelAttribute("usuario") UsuarioCadastroDTO dto) {
        return "usuarios/cadastro";
    }

    @PostMapping("/usuarios/cadastro")
    public String registrar(@Valid @ModelAttribute("usuario") UsuarioCadastroDTO dto,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "usuarios/cadastro";
        }

        try {
            usuarioService.registrar(dto);
            Usuario usuario = usuarioService.autenticar(dto.getEmail(), dto.getSenha());
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(usuario, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/curriculo?usuarioId=" + usuario.getId();
        } catch (IllegalArgumentException | EntityNotFoundException ex) {
            result.reject("erro.negocio", ex.getMessage());
            return "usuarios/cadastro";
        }
    }

    @GetMapping("/login")
    public String telaLogin(@ModelAttribute("login") UsuarioLoginDTO loginDTO) {
        return "usuarios/login";
    }

    @PostMapping("/login")
    public String processarLogin(@Valid @ModelAttribute("login") UsuarioLoginDTO loginDTO,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "usuarios/login";
        }

        try {
            Usuario usuario = usuarioService.autenticar(loginDTO.getEmail(), loginDTO.getSenha());
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(usuario, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/curriculo?usuarioId=" + usuario.getId();
        } catch (IllegalArgumentException | EntityNotFoundException ex) {
            result.reject("erro.negocio", ex.getMessage());
            return "usuarios/login";
        }
    }
}
