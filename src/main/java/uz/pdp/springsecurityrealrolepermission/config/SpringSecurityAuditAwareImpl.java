package uz.pdp.springsecurityrealrolepermission.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.pdp.springsecurityrealrolepermission.entity.Users;

import java.util.Optional;

public class SpringSecurityAuditAwareImpl implements AuditorAware<Users> {
    @Override
    public Optional<Users> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null
                &&authentication.isAuthenticated()
                &&!authentication.getPrincipal().equals("anonymousUser")
        ){
            Users users = (Users) authentication.getPrincipal();
            return Optional.of(users);
        }

        return Optional.empty();
    }
}
