package com.inventory.api.inventory_management.service;

import com.inventory.api.inventory_management.dto.AuthRequest;
import com.inventory.api.inventory_management.dto.EmployeeCreateDto;
import com.inventory.api.inventory_management.dto.EmployeeDto;
import com.inventory.api.inventory_management.entity.Employee;
import com.inventory.api.inventory_management.mapper.EmployeeMapper;
import com.inventory.api.inventory_management.repository.EmployeeRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final EmployeeMapper employeeMapper;

    private final CacheManager cacheManager;

    public EmployeeDto signup(final EmployeeCreateDto dto) {
        log.info("IN AuthenticationService: create");
        if (this.employeeRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new EntityExistsException("The username: " + dto.getUsername() + " already exists.");
        }
        this.passwordEncoder.encode(dto.getPassword());
        final Employee employee = this.employeeRepository.save(this.employeeMapper.createDtoToEntity(dto));
        this.clearCache();
        return this.employeeMapper.entityToDto(employee);
    }

    public Employee authenticate(final AuthRequest request) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        return this.employeeRepository.findByUsername(request.getUsername()).orElseThrow();
    }

    private void clearCache() {
        Objects.requireNonNull(this.cacheManager.getCache("employeeCache")).clear();
        log.info("Cache 'employeeCache' has been cleared successfully.");
    }
}
