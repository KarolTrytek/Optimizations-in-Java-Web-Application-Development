package pl.edu.pk.optimizationsapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8037");
        server.setDescription("Master Thesis: Optimizations in Java");

        Contact myContact = new Contact();
        myContact.setName("Karol Trytek");
        myContact.setEmail("k_trytek@outlook.com");

        Info information = new Info()
                .title("Optimizations in Java API")
                .version("1.0")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
