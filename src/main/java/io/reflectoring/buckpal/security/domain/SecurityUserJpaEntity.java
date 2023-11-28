package io.reflectoring.buckpal.security.domain;

import io.reflectoring.buckpal.common.BaseUserJpaEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "base_user_jpa_entity")
public class SecurityUserJpaEntity extends BaseUserJpaEntity<SecurityUserJpaEntity> {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private String name;
    private String email;
    private String picture;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public SecurityUserJpaEntity update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }
}
