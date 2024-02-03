package com.hampcode.dto;

import com.hampcode.model.ERole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleResponse {
    private Long id;
    private ERole name; //analizar si se cambia a string


}
