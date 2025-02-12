package io.reflectoring.buckpal.security.util;

import io.reflectoring.buckpal.security.domain.SecurityUserJpaEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContextUtils {

    public static SecurityUserJpaEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    (UsernamePasswordAuthenticationToken) authentication;
            Object principal = usernamePasswordAuthenticationToken.getPrincipal();
            if (principal instanceof SecurityUserJpaEntity) {
                return (SecurityUserJpaEntity) principal;
            }
        }
        throw new IllegalArgumentException("User not found");
    }
}
