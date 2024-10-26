package com.multishop.fusiontech.dtos.user;

import com.multishop.fusiontech.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private Gender gender;
    private String image;
    private String password;
}
