package br.com.restartai.restart_ai.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PerfilViewController {

    @GetMapping("/perfil")
    public String paginaPerfil(@RequestParam("usuarioId") Long usuarioId, Model model) {
        model.addAttribute("usuarioId", usuarioId);
        return "usuarios/perfil";
    }
}
