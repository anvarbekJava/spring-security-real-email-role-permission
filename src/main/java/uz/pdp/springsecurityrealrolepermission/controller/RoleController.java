package uz.pdp.springsecurityrealrolepermission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springsecurityrealrolepermission.payload.ApiResponse;
import uz.pdp.springsecurityrealrolepermission.payload.RoleDto;
import uz.pdp.springsecurityrealrolepermission.service.RoleService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    @PreAuthorize("hasAnyAuthority('ADD_ROLE')")
    @PostMapping("/add")
    public HttpEntity<?> add(@Valid @RequestBody RoleDto dto){
        ApiResponse apiResponse = roleService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PreAuthorize("hasAnyAuthority('EDET_ROLE')")
    @PutMapping("/edet/{id}")
    public HttpEntity<?> edet(@Valid @RequestBody RoleDto dto, @PathVariable Integer id){
        ApiResponse apiResponse = roleService.edet(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PreAuthorize("hasAnyAuthority('VIEW_ALL_ROLE')")
    @GetMapping("/get")
    public HttpEntity<?> get(){
        ApiResponse apiResponse = roleService.get();
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize("hasAnyAuthority('VIEW_ROLE')")
    @GetMapping("/get/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id){
        ApiResponse apiResponse = roleService.getOne(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
