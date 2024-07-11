package com.inventory.api.inventory_management.controller;

import com.inventory.api.inventory_management.dto.AuthRequest;
import com.inventory.api.inventory_management.dto.AuthResponse;
import com.inventory.api.inventory_management.dto.EmployeeCreateDto;
import com.inventory.api.inventory_management.entity.Employee;
import com.inventory.api.inventory_management.service.AuthenticationService;
import com.inventory.api.inventory_management.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Employee> register(@RequestBody @Valid final EmployeeCreateDto employeeCreateDto) {
        return ResponseEntity.ok(this.authenticationService.signup(employeeCreateDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid final AuthRequest authRequest) {
        final String jwtToken = this.jwtService.generateToken(this.authenticationService.authenticate(authRequest));
        return ResponseEntity.ok(new AuthResponse(jwtToken, this.jwtService.getExpirationTime()));
    }
}
