package com.example.spring_boot_store_management_api;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.type.LogicalType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;

@Configuration
public class JacksonStrictConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer strictCustomizer() {
        return builder -> builder.postConfigurer(objectMapper -> {

            // Disable global coercion fallback
            objectMapper.configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, false);

            // STRICT STRINGS — forbid numbers, floats, booleans
            objectMapper.coercionConfigFor(LogicalType.Textual)
                    .setCoercion(CoercionInputShape.Integer, CoercionAction.Fail)
                    .setCoercion(CoercionInputShape.Float, CoercionAction.Fail)
                    .setCoercion(CoercionInputShape.Boolean, CoercionAction.Fail);

            // STRICT INTEGERS — forbid floats and textual numbers
            objectMapper.coercionConfigFor(LogicalType.Integer)
                    .setCoercion(CoercionInputShape.Float, CoercionAction.Fail)
                    .setCoercion(CoercionInputShape.String, CoercionAction.Fail);
        });
    }
}
