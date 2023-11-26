package com.tux.iam.dto;

import com.tux.iam.entity.Office;
import com.tux.iam.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    private String userName;
    private  String password;
    private  String email;
    private Role role;
    private Office office;
}
