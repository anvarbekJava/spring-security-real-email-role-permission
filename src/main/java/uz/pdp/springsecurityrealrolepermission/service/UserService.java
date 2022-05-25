package uz.pdp.springsecurityrealrolepermission.service;

import uz.pdp.springsecurityrealrolepermission.entity.Users;
import uz.pdp.springsecurityrealrolepermission.payload.ApiResponse;
import uz.pdp.springsecurityrealrolepermission.payload.UserDto;

import java.util.List;

public interface UserService {
    ApiResponse addUser(UserDto dto);

    ApiResponse edetUser(UserDto dto, Integer id);

    ApiResponse completedUser(Integer id);

    ApiResponse get();

    ApiResponse getOne(Integer id);
}
