package io.reflectoring.buckpal.account.adapter.in.web;

import io.reflectoring.buckpal.account.adapter.out.persistence.user.DomainUserRepository;
import io.reflectoring.buckpal.security.domain.SecurityUserJpaEntity;
import io.reflectoring.buckpal.security.util.UserContextUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
public class UserContextController {

    private final DomainUserRepository domainUserRepository;

    @GetMapping("/v1/user")
    @Transactional
    public String user(Principal principal) {

        log.info("current principal: {}", principal.toString());

        SecurityUserJpaEntity currentUser = UserContextUtils.getCurrentUser();
        log.info("current user: {}", currentUser.toString());

        // 물론 나쁜 코드..
        domainUserRepository.findById(currentUser.getId())
                .orElseThrow(IllegalArgumentException::new)
                .setColumnTest();

        return "user";
    }

    @GetMapping("/v1/user2")
    String user2(@AuthenticationPrincipal SecurityUserJpaEntity principal) {

        log.info("current principal: {}", principal.toString());

        return "user2";
    }
}
