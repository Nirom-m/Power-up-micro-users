package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class UserRequestDto {
    private String name;
    private String lastName;
    private String dniNumber;
    private String phone;
    private LocalDate birthDay;
    private String mail;
    private String password;
    private Long role;
}
