package uz.pdp.springsecurityrealrolepermission.service;

import uz.pdp.springsecurityrealrolepermission.payload.ApiResponse;
import uz.pdp.springsecurityrealrolepermission.payload.RoleDto;

public interface RoleService {
    ApiResponse add(RoleDto dto);

    ApiResponse edet(RoleDto dto, Integer id);

    ApiResponse get();

    ApiResponse getOne(Integer id);
}
