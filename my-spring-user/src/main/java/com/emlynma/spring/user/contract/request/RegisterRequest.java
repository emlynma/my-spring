package com.emlynma.spring.user.contract.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    private String uname;
    private String phone;
    private String email;

    @NotBlank
    private String password;

}
