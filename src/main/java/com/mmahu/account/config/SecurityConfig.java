package com.mmahu.account.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
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
            Map<String, Object> claims = claims(JwtHelper.decode(token).getClaims());
            return Mono.just(new Jwt(token, Instant.now(), Instant.now().plusMillis(100), headers(), claims));
        };
    }

    private Map<String, Object> headers() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", HS256);
        headers.put("typ", "JWT");
        return headers;
    }

    private Map<String, Object> claims(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, Map.class);
        } catch (IOException e) {
            return Collections.emptyMap();
        }
    }


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange().anyExchange().authenticated().and().oauth2ResourceServer().jwt();
        return http.build();

    }

//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/account").permitAll()
//                .anyRequest().authenticated().and().oauth2ResourceServer().jwt();
//    }
}
