package uz.pdp.springsecurityrealrolepermission.payload;

import lombok.Data;
import uz.pdp.springsecurityrealrolepermission.entity.enums.Permission;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class RoleDto {
    @NotBlank
    private String name;

    private String description;

    @NotEmpty
    private List<Permission> permissions;
}
