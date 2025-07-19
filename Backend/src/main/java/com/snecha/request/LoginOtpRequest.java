package com.snecha.request;

import com.snecha.domain.USER_ROLE;

import lombok.Data;
@Data
public class LoginOtpRequest {
private String email;
private String otp;
private USER_ROLE role;

}
