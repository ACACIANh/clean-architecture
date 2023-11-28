package io.reflectoring.buckpal.security.oauth;

import io.reflectoring.buckpal.security.domain.UserJpaEntity;
import io.reflectoring.buckpal.security.domain.UserOAuth;
import io.reflectoring.buckpal.security.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuth2ProviderUser providerUser = OAuth2ProviderUser.of(userNameAttributeName,
                oAuth2User.getAttributes());

        UserJpaEntity userJpaEntity = this.saveOrUpdate(providerUser);

        return new UserOAuth(oAuth2User, providerUser.getKakaoUserInfo(), userJpaEntity);
    }

    private UserJpaEntity saveOrUpdate(OAuth2ProviderUser providerUser) {
        UserJpaEntity userJpaEntity = userRepository.findByEmail(
                        providerUser.getKakaoUserInfo().getKakaoAccount().getEmail())
                .map(e -> e.update(
                                providerUser.getKakaoUserInfo().getKakaoAccount().getProfile().getNickname(),
                                providerUser.getKakaoUserInfo().getProperties().getProfileImage()
                        )
                ).orElse(providerUser.toEntity());

        return userRepository.save(userJpaEntity);
    }
}
