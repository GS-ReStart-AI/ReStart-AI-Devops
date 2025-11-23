package br.com.restartai.restart_ai.service;

import br.com.restartai.restart_ai.domain.Curriculo;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AnaliseCurriculoIaService {

    private final CurriculoService curriculoService;
    private final ChatClient chatClient;

    public AnaliseCurriculoIaService(CurriculoService curriculoService, ChatClient.Builder chatClientBuilder) {
        this.curriculoService = curriculoService;
        this.chatClient = chatClientBuilder.build();
    }

    public String analisarCurriculo(Long curriculoId) {
        return analisarCurriculo(curriculoId, "en");
    }

    public String analisarCurriculo(Long curriculoId, String lang) {
        Curriculo curriculo = curriculoService.buscarPorId(curriculoId);
        if (curriculo.getTexto() == null || curriculo.getTexto().isBlank()) {
            throw new IllegalStateException("Currículo sem texto extraído para análise.");
        }

        String linguagem = (lang == null || lang.isBlank()) ? "en" : lang.toLowerCase();
        boolean portugues = linguagem.startsWith("pt");

        String prompt;

        if (portugues) {
            prompt = """
                    Você é um especialista em carreira e mercado de trabalho.

                    Com base no currículo abaixo, produza uma análise estruturada em QUATRO seções, exatamente neste formato:

                    1. Resumo do perfil profissional
                    - frase 1 sobre o perfil
                    - frase 2 sobre o perfil
                    - até 5 frases no total

                    2. Principais competências e habilidades
                    - competência ou habilidade 1
                    - competência ou habilidade 2
                    - competência ou habilidade 3
                    - (adicione quantas fizerem sentido, em linhas separadas)

                    3. Áreas de atuação atuais possíveis
                    - área 1: breve explicação (1 a 2 frases)
                    - área 2: breve explicação
                    - área 3: breve explicação

                    4. Áreas alternativas para migração de carreira
                    - área alternativa 1: breve justificativa (por que faz sentido para esse perfil)
                    - área alternativa 2: breve justificativa
                    - área alternativa 3: breve justificativa

                    Regras IMPORTANTES para o formato:
                    - Use EXATAMENTE as seções numeradas 1., 2., 3. e 4. nessa ordem.
                    - NÃO repita os títulos das seções dentro do texto (não escreva de novo "Resumo do perfil profissional", etc.).
                    - Comece cada item de lista com "- " (hífen e espaço). Não use outros marcadores como "•".
                    - Não use tabelas.
                    - Não escreva nada fora dessas quatro seções.

                    Agora analise o currículo abaixo e responda seguindo estritamente o formato descrito, em português do Brasil.

                    CURRÍCULO:
                    %s
                    """.formatted(curriculo.getTexto());
        } else {
            prompt = """
                    You are a career and job market specialist.

                    Based on the resume text below, produce a structured analysis in FOUR sections, exactly in this format:

                    1. Summary of the professional profile
                    - sentence 1 about the profile
                    - sentence 2 about the profile
                    - up to 5 sentences in total

                    2. Main skills and competencies
                    - skill or competency 1
                    - skill or competency 2
                    - skill or competency 3
                    - (add as many as make sense, each in a separate line)

                    3. Possible current areas of work
                    - area 1: short explanation (1–2 sentences)
                    - area 2: short explanation
                    - area 3: short explanation

                    4. Alternative areas for career change
                    - alternative area 1: brief justification (why it fits this profile)
                    - alternative area 2: brief justification
                    - alternative area 3: brief justification

                    IMPORTANT formatting rules:
                    - Use EXACTLY the numbered sections 1., 2., 3. and 4. in this order.
                    - DO NOT repeat the section titles inside the text (do not write "Summary of the professional profile" again, etc.).
                    - Start each list item with "- " (dash and space). Do not use other bullets like "•".
                    - Do not use tables.
                    - Do not write anything outside these four sections.

                    Now analyze the resume below and answer strictly following the described format, in English.

                    RESUME:
                    %s
                    """.formatted(curriculo.getTexto());
        }

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}
