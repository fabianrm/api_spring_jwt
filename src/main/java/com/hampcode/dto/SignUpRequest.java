package com.hampcode.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    //Entity: User
    private String email;
    private String password;
    //Entity: UserProfile
    private String firstName;
    private String lastName;
    private String dni;
}
