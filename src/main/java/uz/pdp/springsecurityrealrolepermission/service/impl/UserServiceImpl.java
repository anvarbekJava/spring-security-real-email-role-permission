package uz.pdp.springsecurityrealrolepermission.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.springsecurityrealrolepermission.entity.Role;
import uz.pdp.springsecurityrealrolepermission.entity.Users;
import uz.pdp.springsecurityrealrolepermission.exceptions.ResourceNotFoundException;
import uz.pdp.springsecurityrealrolepermission.payload.ApiResponse;
import uz.pdp.springsecurityrealrolepermission.payload.UserDto;
import uz.pdp.springsecurityrealrolepermission.security.repository.RoleRepositry;
import uz.pdp.springsecurityrealrolepermission.security.repository.UsersRepository;
import uz.pdp.springsecurityrealrolepermission.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RoleRepositry roleRepositry;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse addUser(UserDto dto) {
        if (usersRepository.existsByUsername(dto.getUsername()))
            return new ApiResponse("Bunday foydalanuvchi bor", false);
        Role role = roleRepositry.findById(dto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("Role", "name", null));
        Users users = new Users();
        users.setUsername(dto.getUsername());
        users.setFullName(dto.getFullName());
        users.setPassword(passwordEncoder.encode(dto.getPassword()));
        users.setRole(role);
        users.setEnabled(true);
        usersRepository.save(users);
        return new ApiResponse("User save", true);
    }

    @Override
    public ApiResponse edetUser(UserDto dto, Integer id) {

        Optional<Users> optionalUsers = usersRepository.findById(id);
        if (!optionalUsers.isPresent())
            return new ApiResponse("Bunda users mavjud emas", false);
        Users users = optionalUsers.get();
        users.setFullName(dto.getFullName());
        users.setUsername(dto.getUsername());
        users.setPassword(passwordEncoder.encode(dto.getPassword()));
        Optional<Role> optionalRole = roleRepositry.findById(dto.getRoleId());
        if (!optionalRole.isPresent())
            return new ApiResponse("Bunday role mavjud emas", false);
        Role role = optionalRole.get();
        users.setRole(role);
        users.setEnabled(true);
        usersRepository.save(users);
        return new ApiResponse("User edet qilindi", true);
    }

    @Override
    public ApiResponse completedUser(Integer id) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "id", null));

        if (users.isEnabled()) {
            users.setEnabled(false);
            usersRepository.save(users);
            return new ApiResponse("User ochirildi", true);
        }else {
            users.setEnabled(true);
            usersRepository.save(users);
            return new ApiResponse("User yoqildi", true);
        }
    }

    @Override
    public ApiResponse getOne(Integer id) {
        Optional<Users> optionalUsers = usersRepository.findById(id);
        if (!optionalUsers.isPresent())
            return new ApiResponse("Topilmaadi", false, null);
        Users users = optionalUsers.get();
        UserDto dto = new UserDto(
                users.getFullName(),
                users.getUsername(),
                users.getRole().getName()
        );
        return new ApiResponse("User", true, dto);
    }

    @Override
    public ApiResponse get() {
        List<Users> usersList = usersRepository.findAll();
        List<UserDto> dtoList = new ArrayList<>();
        for (Users users : usersList) {
            UserDto dto = new UserDto(
                    users.getFullName(),
                    users.getUsername(),
                    users.getRole().getName()
            );
            dtoList.add(dto);
        }
        return new ApiResponse("UserLis", true, dtoList);
    }
}
