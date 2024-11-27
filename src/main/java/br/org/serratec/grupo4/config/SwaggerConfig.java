package br.org.serratec.grupo4.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Value("${dominio.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {

        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("URL do servidor do Grupo 4");

        Contact contact = new Contact();
        contact.setEmail("contato@g4dominio.com.br 📧");
        contact.setName("Grupo 4.0");
        contact.setUrl("https://www.g4dominio.com.br");

        License apacheLicense = new License().name("Apache License")
                .url("https://www.apache.org/license/LICENSE-2.0");

        Info info = new Info().title("Grupo 4 👨‍💻 👩‍💻").version("1.0").contact(contact)
                .description("Trabalho Final do Grupo 4.0").termsOfService("https://https://www.g4dominio.com.br/termos")
                .license(apacheLicense);

        SecurityScheme securityScheme = new SecurityScheme()
                .type(Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer))
                .addSecurityItem(securityRequirement)
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme));

    }

}