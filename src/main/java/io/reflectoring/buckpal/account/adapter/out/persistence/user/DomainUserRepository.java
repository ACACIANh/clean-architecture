package io.reflectoring.buckpal.account.adapter.out.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainUserRepository extends JpaRepository<DomainUserJpaEntity, Long> {
}
