package com.shop.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * 엔티티의 값이 생성되고 변경될 때,
 * 현재 로그인한 사용자의 정보를 생성자와 수정자로 지정
 * AuditorAware 인터페이스 구현
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = "";
        if (authentication != null) {
            userId = authentication.getName();
        }

        return Optional.of(userId);
    }
}
