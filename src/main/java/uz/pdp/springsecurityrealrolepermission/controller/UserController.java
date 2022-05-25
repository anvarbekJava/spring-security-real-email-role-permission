package uz.pdp.springsecurityrealrolepermission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springsecurityrealrolepermission.entity.Users;
import uz.pdp.springsecurityrealrolepermission.payload.ApiResponse;
import uz.pdp.springsecurityrealrolepermission.payload.UserDto;
import uz.pdp.springsecurityrealrolepermission.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    @PreAuthorize("hasAnyAuthority('ADD_USER')")
    @PostMapping("/add")
    public HttpEntity<?> addUser(@Valid @RequestBody UserDto dto){
        ApiResponse apiResponse = userService.addUser(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PreAuthorize("hasAnyAuthority('EDET_USER')")
    @PutMapping("/edet/{id}")
    public HttpEntity<?> edetUser(@Valid @RequestBody UserDto dto, @PathVariable Integer id){
        ApiResponse apiResponse = userService.edetUser(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/completed/user/{id}")
    public HttpEntity<?> completedUser(@PathVariable Integer id){
        ApiResponse apiResponse = userService.completedUser(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PreAuthorize("hasAnyAuthority('VIEW_USER')")
    @GetMapping("/get/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id){
        ApiResponse apiResponse = userService.getOne(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PreAuthorize("hasAnyAuthority('VIEW_ALL_USER')")
    @GetMapping("/get")
    public HttpEntity<?> get(){
         ApiResponse apiResponse = userService.get();
         return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
