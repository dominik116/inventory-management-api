package com.inventory.api.inventory_management.controller;

import com.inventory.api.inventory_management.dto.AuthRequest;
import com.inventory.api.inventory_management.dto.AuthResponse;
import com.inventory.api.inventory_management.dto.EmployeeCreateDto;
import com.inventory.api.inventory_management.dto.EmployeeDto;
import com.inventory.api.inventory_management.entity.Employee;
import com.inventory.api.inventory_management.service.AuthenticationService;
import com.inventory.api.inventory_management.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ResponseEntity<EmployeeDto> register(@RequestBody @Valid final EmployeeCreateDto employeeCreateDto) {
        return ResponseEntity.created(URI.create("/auth/signup")).body(this.authenticationService.signup(employeeCreateDto));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid final AuthRequest authRequest) {
        final Employee employee = this.authenticationService.authenticate(authRequest);
        final String jwtToken = this.jwtService.generateToken(employee);
        return ResponseEntity.ok(new AuthResponse(jwtToken, this.jwtService.getExpirationTime(), employee.getRole()));
    }
}
