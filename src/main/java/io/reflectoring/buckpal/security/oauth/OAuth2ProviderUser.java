package io.reflectoring.buckpal.security.oauth;

import com.google.gson.Gson;
import io.reflectoring.buckpal.security.domain.KakaoUser;
import io.reflectoring.buckpal.security.domain.KakaoUser.KakaoAccount;
import io.reflectoring.buckpal.security.domain.UserJpaEntity;
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
    private final KakaoUser kakaoUserInfo;

    public static OAuth2ProviderUser of(String userNameAttributeName, Map<String, Object> attributes) {
        return ofKakao(userNameAttributeName, attributes);
    }

    private static OAuth2ProviderUser ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Gson gson = GsonUtils.getLowerCaseWithUnderscores();

        String jsonValue = gson.toJson(attributes);

        KakaoUser kakaoUserInfo = gson.fromJson(jsonValue, KakaoUser.class);

        return new OAuth2ProviderUser(attributes, userNameAttributeName, kakaoUserInfo);
    }

    public UserJpaEntity toEntity() {
        KakaoAccount kakaoAccount = kakaoUserInfo.getKakaoAccount();
        return new UserJpaEntity(null,
                kakaoAccount.getProfile().getNickname(),
                kakaoAccount.getEmail(),
                kakaoUserInfo.getProperties().getProfileImage(),
                UserRole.MEMBER);
    }
}
