package io.reflectoring.buckpal.security;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @GetMapping("/v1/custom/token")
    String token(@RequestParam("token") String token) {
        return token;
    }

    @GetMapping("/")
    String index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/kakao");
        return "redirect";
    }
}
