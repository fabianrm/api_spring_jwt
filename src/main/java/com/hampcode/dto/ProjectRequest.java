package com.hampcode.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {
    private String name;
    private Long userId;
}
