package be.kdg.java2.project.presentation.api.dto;

import be.kdg.java2.project.domain.Role;
import be.kdg.java2.project.utils.ValidPassword;
import org.springframework.lang.NonNull;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

public class UserDTO {
    @NotNull
    @NotEmpty(message = "The username should be given.")
    private String username;

    @NotNull
    @NotEmpty(message = "The email should be given.")
    private String email;

    @ValidPassword
    @NonNull
    @NotBlank(message = "Password is mandatory")
    private String password;

    @PasswordMatch
    @NonNull
    @NotBlank(message = "Confirm Password is mandatory")
    private String matchingPassword;

    @NotNull
    @NotEmpty
    private int architectFirmId;

    // Getters

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public int getArchitectFirmId() {
        return architectFirmId;
    }

    // Setters

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public void setArchitectFirmId(int architectFirmId) {
        this.architectFirmId = architectFirmId;
    }
}
