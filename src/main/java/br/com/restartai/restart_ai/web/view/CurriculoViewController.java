package br.com.restartai.restart_ai.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CurriculoViewController {

    @GetMapping("/curriculo")
    public String paginaCurriculo(@RequestParam("usuarioId") Long usuarioId,
                                  @RequestParam(value = "lang", required = false) String lang,
                                  Model model) {
        model.addAttribute("usuarioId", usuarioId);

        String iaLang = (lang != null && !lang.isBlank()) ? lang : "en";
        model.addAttribute("iaLang", iaLang);

        return "curriculo/curriculo";
    }
}
