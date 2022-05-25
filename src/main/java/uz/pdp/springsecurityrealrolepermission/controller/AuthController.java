package uz.pdp.springsecurityrealrolepermission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springsecurityrealrolepermission.payload.ApiResponse;
import uz.pdp.springsecurityrealrolepermission.payload.LoginDto;
import uz.pdp.springsecurityrealrolepermission.payload.RegisterDto;
import uz.pdp.springsecurityrealrolepermission.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?> register(@Valid @RequestBody RegisterDto dto){
        ApiResponse apiResponse = authService.register(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/verify")
    public HttpEntity<?> verify(@RequestParam String sendingEmail, @RequestParam Integer emailCode){
        ApiResponse apiResponse = authService.verify(sendingEmail, emailCode);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody LoginDto dto){
        ApiResponse apiResponse = authService.login(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
