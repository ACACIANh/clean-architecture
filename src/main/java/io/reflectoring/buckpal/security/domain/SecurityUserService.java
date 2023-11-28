package io.reflectoring.buckpal.security.domain;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserService {

    private final SecurityUserRepository userRepository;

    public Optional<SecurityUserJpaEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
