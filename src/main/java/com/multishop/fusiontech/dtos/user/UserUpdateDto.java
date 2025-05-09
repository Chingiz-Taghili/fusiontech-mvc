package com.multishop.fusiontech.dtos.user;

import com.multishop.fusiontech.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    private String name;
    private String surname;
    private LocalDate birthdate;
    private String email;
    private Gender gender;
    private String image;
    private String password;
}
