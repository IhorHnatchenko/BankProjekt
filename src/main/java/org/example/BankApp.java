package org.example;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Bank application", description = "Bank application", version = "1.0",
        contact = @Contact(name = "Ihor")))
@SecurityScheme(name = "general", in = SecuritySchemeIn.HEADER, type = SecuritySchemeType.HTTP, scheme = "basic")
@SpringBootApplication
public class BankApp {
    public static void main(String[] args) {
        SpringApplication.run(BankApp.class, args);
    }
}