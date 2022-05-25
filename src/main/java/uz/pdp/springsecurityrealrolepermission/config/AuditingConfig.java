package uz.pdp.springsecurityrealrolepermission.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import uz.pdp.springsecurityrealrolepermission.entity.Users;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {
    @Bean
    AuditorAware<Users> auditorAware(){
        return new SpringSecurityAuditAwareImpl();
    }
}