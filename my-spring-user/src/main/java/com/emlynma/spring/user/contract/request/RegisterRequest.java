package com.emlynma.spring.user.contract.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
