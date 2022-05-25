package uz.pdp.springsecurityrealrolepermission.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.springsecurityrealrolepermission.constants.AppConstans;
import uz.pdp.springsecurityrealrolepermission.entity.Role;
import uz.pdp.springsecurityrealrolepermission.entity.Users;
import uz.pdp.springsecurityrealrolepermission.exceptions.ResourceNotFoundException;
import uz.pdp.springsecurityrealrolepermission.payload.ApiResponse;
import uz.pdp.springsecurityrealrolepermission.payload.LoginDto;
import uz.pdp.springsecurityrealrolepermission.payload.RegisterDto;
import uz.pdp.springsecurityrealrolepermission.security.repository.RoleRepositry;
import uz.pdp.springsecurityrealrolepermission.security.repository.UsersRepository;
import uz.pdp.springsecurityrealrolepermission.security.JwtProvider;
import uz.pdp.springsecurityrealrolepermission.service.AuthService;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {
    @Autowired
    UsersRepository usersRepository;
    @Lazy
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepositry roleRepositroy;
    @Lazy
    @Autowired
    JavaMailSender javaMailSender;
    @Lazy
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    @Override
    public ApiResponse register(RegisterDto dto) {
        if (!dto.getPassword().equals(dto.getPrePassword()))
            return new ApiResponse("Parollar mos kelmadi", false);
        if (usersRepository.existsByUsername(dto.getUsername()))
            return new ApiResponse("Bunday foydalanuvchi mavjud qayta kiriting", false);
        Role role = roleRepositroy.findByName(AppConstans.USER).orElseThrow(() -> new ResourceNotFoundException("RoleName", "name", null));

        Users users = new Users(
                dto.getFullName(),
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                role,
                (int)(Math.random()*10000+1000)
        );
        usersRepository.save(users);
        sendEmail(users.getUsername(), users.getEmailCode());
        return new ApiResponse("Royhatdan otinggiz Tasdiqlang", true);

    }

    @Override
    public ApiResponse verify(String sendingEmail, Integer emailCode) {
        Optional<Users> optionalUsers = usersRepository.findByUsernameAndEmailCode(sendingEmail, emailCode);
        if (!optionalUsers.isPresent())
            return new ApiResponse("Topilmadi qayta urinib koring", false);
        Users users = optionalUsers.get();
        users.setEmailCode(null);
        users.setEnabled(true);
        usersRepository.save(users);
        return new ApiResponse("Tasdiqlandi", true);
    }

    @Override
    public ApiResponse login(LoginDto dto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
            Users users = (Users) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(users.getUsername(), users.getRole());
            return new ApiResponse("Token", true, token);
        }catch (BadCredentialsException badCredentialsException){
            return new ApiResponse("Parol yoki login xato", false);
        }
    }

    public void sendEmail(String sendingEmail, Integer emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Test@pdp131.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Akkountni tasdiqlash");
            mailMessage.setText("<a href = 'http://localhost:8080/api/auth/verifyEmail?sendingEmail = "+sendingEmail+"&emailCode="+emailCode+"'></a>");
            javaMailSender.send(mailMessage);
        } catch (Exception ignored) {
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username"));
    }
}
