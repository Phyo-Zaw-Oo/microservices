package com.phyo.accounts;

import com.phyo.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservices REST API Documentation",
				description = "Phyo Bank Accounts microservices REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Phyo Zaw Oo",
						email= "phyozawoo@gmail.com",
						url = "www.phyozawoo.com"
				),
				license = @License(
						name = "Phyo 2.0",
						url = "www.phyozawoo.com"
				)

		),

		externalDocs = @ExternalDocumentation(
				description =  "Phyo Accounts microservice REST API Documentation",
				url = "https://www.phyo.com/swagger-ui.html"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
