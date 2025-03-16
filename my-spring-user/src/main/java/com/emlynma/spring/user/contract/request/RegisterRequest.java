package com.emlynma.spring.user.contract.request;

import lombok.Data;

@Data
public class RegisterRequest {

    private String uname;
    private String phone;
    private String email;

    private String password;

}
