package br.com.restartai.restart_ai.security;

import br.com.restartai.restart_ai.domain.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUsuarioService {

    public Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof Usuario) {
            return (Usuario) principal;
        }
        return null;
    }

    public Long getUsuarioId() {
        Usuario usuario = getUsuarioAutenticado();
        if (usuario == null) {
            return null;
        }
        return usuario.getId();
    }
}
