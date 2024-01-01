package vttp.ssf.miniproject.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {
    
    @NotEmpty(message = "Please enter your username")
    @Size(min = 3, max = 16, message = "Username must be between 3 to 16 characters")
    private String username;

    @NotEmpty(message = "Please enter your password")
    private String password;

    @NotEmpty(message = "Please re-enter your password")
    private String password2;
}
