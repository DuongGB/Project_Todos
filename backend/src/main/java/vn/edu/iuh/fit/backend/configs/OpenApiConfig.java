/*
 * @ {#} OpenApiConfig.java   1.0     8/20/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package vn.edu.iuh.fit.backend.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @description: Configuration class for OpenAPI documentation.
 * @author: Nguyen Tan Thai Duong
 * @date:   8/20/2025
 * @version:    1.0
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Project Todos API")
                        .version("1.0")
                        .description("API documentation for Project Todos"));
    }
}

