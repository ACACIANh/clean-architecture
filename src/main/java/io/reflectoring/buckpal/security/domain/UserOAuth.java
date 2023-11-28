package io.reflectoring.buckpal.security.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RequiredArgsConstructor
public class UserOAuth implements OAuth2User, Serializable {

    private final OAuth2User oAuth2User;
    private final KakaoUserInfo KakaoUser;
    private final SecurityUserJpaEntity user;

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }

    public String getIdentifier() {
        return user.getEmail();
    }
}
