package com.inventory.api.inventory_management.service;

import com.inventory.api.inventory_management.dto.AuthRequest;
import com.inventory.api.inventory_management.dto.EmployeeCreateDto;
import com.inventory.api.inventory_management.entity.Employee;
import com.inventory.api.inventory_management.mapper.EmployeeMapper;
import com.inventory.api.inventory_management.repository.EmployeeRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final EmployeeMapper employeeMapper;

    public AuthenticationService(
            EmployeeRepository employeeRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            EmployeeMapper employeeMapper
    ) {
        this.authenticationManager = authenticationManager;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeMapper = employeeMapper;
    }

    public Employee signup(final EmployeeCreateDto dto) {
        final Employee employee = this.employeeMapper.createDtoToEntity(dto);
        employee.setPassword(this.passwordEncoder.encode(dto.getPassword()));

        return this.employeeRepository.save(employee);
    }

    public Employee authenticate(final AuthRequest request) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        return this.employeeRepository.findByUsername(request.getUsername()).orElseThrow();
    }
}
