package com.hampcode.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    //User
    private Long id;
    private String email;
    //UserProfile
    private String firstName;
    private String lastName;
    private String dni;
    //Role
    private List<String> roles;
}
