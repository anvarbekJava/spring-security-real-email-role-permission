package uz.pdp.springsecurityrealrolepermission.service;

import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.springsecurityrealrolepermission.payload.ApiResponse;
import uz.pdp.springsecurityrealrolepermission.payload.LoginDto;
import uz.pdp.springsecurityrealrolepermission.payload.RegisterDto;

public interface AuthService {
    ApiResponse register(RegisterDto dto);

    ApiResponse verify(String sendingEmail, Integer emailCode);

    ApiResponse login(LoginDto dto);

    UserDetails loadUserByUsername(String email);
}
