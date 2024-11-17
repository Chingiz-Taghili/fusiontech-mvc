package com.multishop.fusiontech.dtos.user;

import com.multishop.fusiontech.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
    private String name;
    private String surname;
    private LocalDate birthdate;
    private String image;
    private Gender gender;
    private String email;
    private String password;
}
