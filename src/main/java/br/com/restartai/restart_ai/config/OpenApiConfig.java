package br.com.restartai.restart_ai.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI restartAiOpenAPI() {
        String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Restart.Ai API")
                        .description("API para realocação de pessoas no mercado de trabalho usando IA e análise de currículos em PDF.")
                        .version("v1")
                        .contact(new Contact()
                                .name("Restart.Ai")
                                .email("contato@restart.ai"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
    }

    @Bean
    public OpenApiCustomizer restartAiTagsOrderCustomizer() {
        return openApi -> {
            List<Tag> orderedTags = new ArrayList<>();

            orderedTags.add(new Tag().name("1. Usuários"));
            orderedTags.add(new Tag().name("2. Currículos"));
            orderedTags.add(new Tag().name("3. Vagas"));
            orderedTags.add(new Tag().name("4. Candidaturas"));

            List<Tag> existingTags = openApi.getTags();
            if (existingTags != null) {
                for (Tag tag : existingTags) {
                    boolean alreadyAdded = orderedTags.stream()
                            .anyMatch(t -> t.getName().equals(tag.getName()));
                    if (!alreadyAdded) {
                        orderedTags.add(tag);
                    }
                }
            }

            openApi.setTags(orderedTags);
        };
    }
}
