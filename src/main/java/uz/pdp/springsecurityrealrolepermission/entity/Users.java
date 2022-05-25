package uz.pdp.springsecurityrealrolepermission.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.springsecurityrealrolepermission.entity.enums.Permission;
import uz.pdp.springsecurityrealrolepermission.entity.template.AbstractEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Users extends AbstractEntity implements UserDetails {

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(optional = false)
    private Role role;

    private Integer emailCode;

    private boolean enabled;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Permission> permissionList = this.role.getPermissions();
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        for (Permission permission : permissionList) {
                grantedAuthoritySet.add(new SimpleGrantedAuthority(permission.name()));
        }
        return grantedAuthoritySet;
    }

    public Users(String fullName, String username, String password, Role role, Integer emailCode) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.emailCode = emailCode;
    }

    public Users(String fullName, String username, String password, Role role, boolean enabled) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }
}
