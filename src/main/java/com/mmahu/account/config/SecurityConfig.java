package com.mmahu.account.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.oauth2.jose.jws.JwsAlgorithms.HS256;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resource.jwt.key-value}")
    String key;

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return (token) -> {
            String json = JwtHelper.decode(token).getClaims();
            Map<String, Object> claims = toObject(json, Map.class);
            JwtDto jwt = toObject(json, JwtDto.class);
            return Mono.just(new Jwt(token, jwt.getIat(), jwt.getExp(), headers(), claims));
        };
    }

    private Map<String, Object> headers() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", HS256);
        headers.put("typ", "JWT");
        return headers;
    }

    private <T> T toObject(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Cannot deserialize Jwt token");
        }
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .pathMatchers("/seller").hasAuthority("SCOPE_ROLE_SELLER")
                .pathMatchers("/buyer").hasAuthority("SCOPE_ROLE_BUYER")
                .and().oauth2ResourceServer().jwt();
        return http.build();
    }

}
