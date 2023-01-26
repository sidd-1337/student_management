package com.studentmanagement.studentmanagement.Controller;

import com.studentmanagement.studentmanagement.Dto.AuthResponse;
import com.studentmanagement.studentmanagement.Dto.UserDTO;
import com.studentmanagement.studentmanagement.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.userRegister(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> userLogin(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.userLogin(userDTO.getUsername(), userDTO.getPassword()));
    }
}
