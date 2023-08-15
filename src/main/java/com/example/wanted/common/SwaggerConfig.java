package com.example.wanted.common;

import com.example.wanted.board.adapter.in.web.BoardController;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class SwaggerConfig {
    @Bean
    public OperationCustomizer customize() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            if (handlerMethod.getBeanType().equals(BoardController.class)) {
                operation.addParametersItem(new Parameter()
                        .in("header")
                        .required(true)
                        .description("JWT token")
                        .name("Authorization"));
            }
            return operation;
        };
    }
}
