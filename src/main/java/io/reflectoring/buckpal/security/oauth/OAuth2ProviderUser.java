package io.reflectoring.buckpal.security.oauth;

import com.google.gson.Gson;
import io.reflectoring.buckpal.security.domain.KakaoUserInfo;
import io.reflectoring.buckpal.security.domain.KakaoUserInfo.KakaoAccount;
import io.reflectoring.buckpal.security.domain.SecurityUserJpaEntity;
import io.reflectoring.buckpal.security.domain.UserRole;
import io.reflectoring.buckpal.security.util.GsonUtils;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OAuth2ProviderUser {

    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final KakaoUserInfo kakaoUserInfo;

    public static OAuth2ProviderUser of(String userNameAttributeName, Map<String, Object> attributes) {
        return ofKakao(userNameAttributeName, attributes);
    }

    private static OAuth2ProviderUser ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Gson gson = GsonUtils.getLowerCaseWithUnderscores();

        String jsonValue = gson.toJson(attributes);

        KakaoUserInfo kakaoUserInfo = gson.fromJson(jsonValue, KakaoUserInfo.class);

        return new OAuth2ProviderUser(attributes, userNameAttributeName, kakaoUserInfo);
    }

    public SecurityUserJpaEntity toEntity() {
        KakaoAccount kakaoAccount = kakaoUserInfo.getKakaoAccount();
        return new SecurityUserJpaEntity(
                kakaoAccount.getProfile().getNickname(),
                kakaoAccount.getEmail(),
                kakaoUserInfo.getProperties().getProfileImage(),
                UserRole.MEMBER);
    }
}
