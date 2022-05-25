package uz.pdp.springsecurityrealrolepermission.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterDto {
    @NotNull
    private String fullName;

    @Email(message="{register.email.invalid}")
    @NotBlank(message="{register.email.invalid}")
    private String username;

    @Size(min=8, max=15, message="{register.password.size}")
    private String password;

    @NotNull
    private String prePassword;
}
