package com.example.server

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.client.oidc.authentication.OidcIdTokenDecoderFactory
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoderFactory
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf{ it.disable() }
            .authorizeHttpRequests{
                it.requestMatchers("/api/todos").authenticated()
                it.requestMatchers("/auth/user").authenticated()
                it.requestMatchers("/logout").permitAll()
            }
            .oauth2Login {
                it.defaultSuccessUrl("http://localhost:5173", true)
            }
            .logout {
                it.logoutSuccessUrl("http://localhost:5173")
            }
        return http.build()
    }

    @Bean
    fun idTokenDecoderFactory(): JwtDecoderFactory<ClientRegistration> {
        val idTokenDecoderFactory = OidcIdTokenDecoderFactory()
        idTokenDecoderFactory.setJwsAlgorithmResolver { clientRegistration ->
            if (clientRegistration.registrationId == "line") {
                MacAlgorithm.HS256
            } else {
                SignatureAlgorithm.RS256
            }
        }
        return idTokenDecoderFactory
    }
}

