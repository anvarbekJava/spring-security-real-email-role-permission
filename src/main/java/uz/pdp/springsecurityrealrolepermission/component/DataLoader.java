package uz.pdp.springsecurityrealrolepermission.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.springsecurityrealrolepermission.constants.AppConstans;
import uz.pdp.springsecurityrealrolepermission.entity.Role;
import uz.pdp.springsecurityrealrolepermission.entity.Users;
import uz.pdp.springsecurityrealrolepermission.entity.enums.Permission;
import uz.pdp.springsecurityrealrolepermission.security.repository.RoleRepositry;
import uz.pdp.springsecurityrealrolepermission.security.repository.UsersRepository;

import java.util.Arrays;

import static uz.pdp.springsecurityrealrolepermission.entity.enums.Permission.*;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UsersRepository userRepository;

    @Autowired
    RoleRepositry roleRepository;

    @Lazy
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String initialMode;
    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")){
            Permission[] roles = Permission.values();
            Role admin = roleRepository.save(new Role(AppConstans.ADMIN,
                    Arrays.asList(roles),
                    "Admin boshqaradi"
            ));

            Role user = roleRepository.save(new Role(AppConstans.USER,
                    Arrays.asList(ADD_COMMENT, EDET_COMMENT, DELETE_MY_COMMENT)
                    ,"User boshqaradi"
            ));
            userRepository.save(new Users(
                    "Anvarbek Turdaliyev",
                    "admin@gmail.com",
                    passwordEncoder.encode("12345678"),
                    admin,
                    true
            ));
            userRepository.save(new Users(
                    "Asrorbek Turdaliyev",
                    "user@gmail.com",
                    passwordEncoder.encode("12345678"),
                    user,
                    true
            ));
        }
    }
}
