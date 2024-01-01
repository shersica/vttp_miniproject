package vttp.ssf.miniproject.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    
    @NotEmpty(message = "Please enter your username")
    @Size(min = 3, max = 16, message = "Username must be between 3 to 16 characters")
    private String username;

    @NotEmpty(message = "Please enter your password")
    private String password;

    public JsonObject toJson() {
        JsonObject jsonObject = Json.createObjectBuilder()
            .add("username", this.username)
            .add("password", this.password)
            .build();
        
        return jsonObject;
    }
}
