package io.reflectoring.buckpal.security.oauth;

import io.reflectoring.buckpal.security.jwt.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String generateToken = jwtProvider.generateToken(authentication);

        String targetUri = UriComponentsBuilder.fromUriString("/v1/custom/token")
                .queryParam("token", generateToken)
                .build().toUriString();

        this.getRedirectStrategy().sendRedirect(request, response, targetUri);
        log.info("generate token : [{}]", generateToken);
    }
}
