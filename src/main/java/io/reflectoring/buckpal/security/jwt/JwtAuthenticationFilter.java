package io.reflectoring.buckpal.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.reflectoring.buckpal.security.domain.SecurityUserJpaEntity;
import io.reflectoring.buckpal.security.domain.SecurityUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String[] excludedUrls;
    private final SecurityUserService userService;
    private final JwtProvider jwtProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Stream.of(excludedUrls).anyMatch(url -> new AntPathMatcher().match(url, request.getServletPath()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authorizationHeader = jwtProvider.getAuthorizationHeader(request);
            String token = jwtProvider.getToken(authorizationHeader);
            Jws<Claims> validateToken = jwtProvider.validateToken(token);
            Claims claims = validateToken.getBody();
            String email = claims.getSubject();

            SecurityUserJpaEntity user = userService.findByEmail(email)
                    .orElseThrow(() -> new IllegalStateException("NotFoundUser email: " + email));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    Stream.of(user.getRole().getRole())
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception ex) {
            log.error("Failed to authenticate user: {}", ex.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
