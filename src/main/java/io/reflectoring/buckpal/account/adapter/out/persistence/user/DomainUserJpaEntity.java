package io.reflectoring.buckpal.account.adapter.out.persistence.user;

import io.reflectoring.buckpal.common.BaseUserJpaEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "base_user_jpa_entity")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DomainUserJpaEntity extends BaseUserJpaEntity<DomainUserJpaEntity> {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private String columnTest;

    public void setColumnTest() {
        columnTest = "success";
    }
}
