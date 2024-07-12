package com.inventory.api.inventory_management.controller;

import com.inventory.api.inventory_management.dto.AuthRequest;
import com.inventory.api.inventory_management.dto.AuthResponse;
import com.inventory.api.inventory_management.dto.EmployeeCreateDto;
import com.inventory.api.inventory_management.entity.Employee;
import com.inventory.api.inventory_management.service.AuthenticationService;
import com.inventory.api.inventory_management.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
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
        final Employee employee = this.authenticationService.authenticate(authRequest);
        final String jwtToken = this.jwtService.generateToken(employee);
        return ResponseEntity.ok(new AuthResponse(jwtToken, this.jwtService.getExpirationTime(), employee.getRole()));
    }
}
