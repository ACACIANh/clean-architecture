package io.reflectoring.buckpal.security;

import io.reflectoring.buckpal.security.domain.UserRole;
import io.reflectoring.buckpal.security.domain.UserService;
import io.reflectoring.buckpal.security.jwt.JwtAuthenticationEntryPoint;
import io.reflectoring.buckpal.security.jwt.JwtAuthenticationFilter;
import io.reflectoring.buckpal.security.jwt.JwtProvider;
import io.reflectoring.buckpal.security.oauth.OAuth2AuthenticationSuccessHandler;
import io.reflectoring.buckpal.security.oauth.OAuth2UserService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final OAuth2UserService customOAuth2UserService;
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private String[] excludedUrls = {
            "/",
            "/favicon.ico",
            "/css/**",
            "/images/**",
            "/js/**",
            "/h2-console/**",
            "/v1/custom/**",
            // 필요시 url 추가
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector)
            throws Exception {

        http.httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
                .oauth2Login(oauth -> {
                    oauth.userInfoEndpoint(config -> {
                        config.userService(customOAuth2UserService);
                    });
                    oauth.successHandler(oAuth2AuthenticationSuccessHandler);
                })
                .authorizeHttpRequests(authorize -> {
                    Arrays.stream(excludedUrls)
                            .forEach(url -> authorize.requestMatchers(new AntPathRequestMatcher(url)).permitAll());
                    authorize
                            .requestMatchers(new MvcRequestMatcher.Builder(introspector)
                                    .pattern(HttpMethod.OPTIONS, "/**")).permitAll()
                            .requestMatchers(new MvcRequestMatcher.Builder(introspector)
                                    .pattern("/api/v1/**")).hasAnyRole(UserRole.MEMBER.name(), UserRole.ADMIN.name())
                            .anyRequest().authenticated();
                })
                .exceptionHandling(exceptionHandle ->
                        exceptionHandle.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(excludedUrls, userService, jwtProvider),
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }
}
