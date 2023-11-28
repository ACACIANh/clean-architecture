package io.reflectoring.buckpal.security.domain;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<UserJpaEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
