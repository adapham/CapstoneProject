package vn.com.edu.fpt.capstoneproject.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "usernameOrEmail not blank")
    private String usernameOrEmail;

    @NotBlank(message = "password not blank")
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
