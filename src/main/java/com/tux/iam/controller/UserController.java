package com.tux.iam.controller;

import com.tux.iam.dto.UpdateDTO;
import com.tux.iam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/iam/v1/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/update/role")
    public ResponseEntity<String> updateUserRole(@RequestBody UpdateDTO updateDTO){
        return  ResponseEntity.ok(userService.updateUserRole(updateDTO));
    }

    @PostMapping("/assign/office")
    public ResponseEntity<String> assignOffice(@RequestBody UpdateDTO updateDTO){
        return  ResponseEntity.ok(userService.assignOffice(updateDTO));
    }
}
