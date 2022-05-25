package uz.pdp.springsecurityrealrolepermission.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.springsecurityrealrolepermission.entity.Role;
import uz.pdp.springsecurityrealrolepermission.payload.ApiResponse;
import uz.pdp.springsecurityrealrolepermission.payload.RoleDto;
import uz.pdp.springsecurityrealrolepermission.security.repository.RoleRepositry;
import uz.pdp.springsecurityrealrolepermission.service.RoleService;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepositry roleRepositry;

    @Override
    public ApiResponse add(RoleDto dto) {
        if (roleRepositry.existsByName(dto.getName()))
            return new ApiResponse("Bunday role mavjud", false);
        Role role = new Role();
        role.setName(dto.getName());
        role.setPermissions(dto.getPermissions());
        role.setDescription(dto.getDescription());
        roleRepositry.save(role);
        return new ApiResponse("Saqlandi", true);
    }

    @Override
    public ApiResponse edet(RoleDto dto, Integer id) {
        Optional<Role> roleOptional = roleRepositry.findById(id);
        if (!roleOptional.isPresent())
            return new ApiResponse("Topilmadi", false);
        Role role = roleOptional.get();
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        role.setPermissions(dto.getPermissions());
        roleRepositry.save(role);
        return new ApiResponse("Edet role", true);
    }

    @Override
    public ApiResponse get() {
        List<Role> roles = roleRepositry.findAll();
        return new ApiResponse("Roles", true, roles);
    }

    @Override
    public ApiResponse getOne(Integer id) {
        Optional<Role> roleOptional = roleRepositry.findById(id);
        if (!roleOptional.isPresent())
            return new ApiResponse("Topilmadi", false);
        Role role = roleOptional.get();
        return new ApiResponse("Role", true, role);
    }
}
