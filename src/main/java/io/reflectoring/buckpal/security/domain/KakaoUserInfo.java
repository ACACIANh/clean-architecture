package io.reflectoring.buckpal.security.domain;

import lombok.Data;

@Data
public class KakaoUserInfo {
    private long id;
    private String connectedAt;
    private Properties properties;
    private KakaoAccount kakaoAccount;

    @Data
    public static class Properties {
        private String nickname;
        private String profileImage;
        private String thumbnailImage;
    }

    @Data
    public static class KakaoAccount {
        private boolean profileNicknameNeedsAgreement;
        private boolean profileImageNeedsAgreement;
        private Profile profile;
        private boolean hasEmail;
        private boolean emailNeedsAgreement;
        private boolean isEmailValid;
        private boolean isEmailVerified;
        private String email;

        @Data
        public static class Profile {
            private String nickname;
            private String thumbnailImageUrl;
            private String profileImageUrl;
            private boolean isDefaultImage;
        }
    }
}
