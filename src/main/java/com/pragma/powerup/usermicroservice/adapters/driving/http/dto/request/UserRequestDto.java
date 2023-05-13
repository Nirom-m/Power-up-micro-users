package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class UserRequestDto {
    @NotBlank(message = "You must enter the name")
    private String name;
    @NotBlank(message = "You must enter the lastname")
    private String lastName;
    @NotBlank(message = "You must enter the dniNumber")
    @Pattern(regexp = "^[1-9][0-9]*$", message = "The dniNumber must contain only numbers and cannot start at 0")
    private String dniNumber;
    @NotBlank(message = "You must enter the phone number")
    @Pattern(regexp = "^(\\+57)([3]\\d{9})$", message = "The format of the mobile number is incorrect")
    private String phone;
    @NotNull(message = "You must enter the date of birth")
    private LocalDate birthDay;
    @NotBlank(message = "You must enter the mail")
    @Email (message = "The mail format is not valid")
    private String mail;
    @NotBlank (message = "You must enter the password")
    private String password;
    private Long role;

}
