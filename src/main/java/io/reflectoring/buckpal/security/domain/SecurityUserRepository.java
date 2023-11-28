package io.reflectoring.buckpal.security.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityUserRepository extends JpaRepository<SecurityUserJpaEntity, Long> {

    Optional<SecurityUserJpaEntity> findByEmail(String email);
}
