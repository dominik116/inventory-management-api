package com.inventory.api.inventory_management.service;

import com.inventory.api.inventory_management.dto.EmployeeCreateDto;
import com.inventory.api.inventory_management.dto.EmployeeDto;
import com.inventory.api.inventory_management.dto.PagingDto;
import com.inventory.api.inventory_management.entity.Employee;
import com.inventory.api.inventory_management.mapper.EmployeeMapper;
import com.inventory.api.inventory_management.repository.EmployeeRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository repository;

    private final EmployeeMapper mapper;

    private final PasswordEncoder passwordEncoder;

    private final CacheManager cacheManager;

    public EmployeeDto create(final EmployeeCreateDto dto) {
        log.info("IN EmployeeService: create");
        if (this.repository.findByUsername(dto.getUsername()).isPresent()) {
            throw new EntityExistsException("The username: " + dto.getUsername() + " already exists.");
        }
        final Employee employee = this.repository.save(this.mapper.createDtoToEntity(dto));
        this.clearCache();
        return this.mapper.entityToDto(employee);
    }

    public EmployeeDto findByUsername(final String username) {
        log.info("IN EmployeeService: findByUsername");
        return this.mapper.entityToDto(this.repository.findByUsername(username).orElseThrow(EntityNotFoundException::new));
    }

    @Cacheable(value = "employeeCache", key = "'page:' + #page + ',size:' + #size")
    public PagingDto<EmployeeDto> findAll(final int page, final int size) {
        log.info("IN EmployeeService: findAll");
        return this.mapper.toPagingDto(this.repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"))));
    }

    public void putUpdate(final Long id, final EmployeeCreateDto dto) {
        log.info("IN EmployeeService: putUpdate");
        final Employee entity = this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
        this.updateEntityFields(entity, dto);
        this.repository.save(entity);
        this.clearCache();
        log.info("OUT EmployeeService: putUpdate");
    }

    public void deleteById(final Long id) {
        log.info("IN EmployeeService: deleteById");
        this.repository.deleteById(id);
        this.clearCache();
        log.info("OUT EmployeeService: deleteById");
    }

    private void clearCache() {
        Objects.requireNonNull(this.cacheManager.getCache("employeeCache")).clear();
        log.info("Cache 'employeeCache' has been cleared successfully.");
    }

    private void updateEntityFields(final Employee employee, final EmployeeCreateDto dto) {
        if (dto.getUsername() != null && !dto.getUsername().equals(employee.getUsername())) {
            employee.setUsername(dto.getUsername());
        }

        if (dto.getEmail() != null && !dto.getEmail().equals(employee.getEmail())) {
            employee.setEmail(dto.getEmail());
        }

        if (dto.getName() != null && !dto.getName().equals(employee.getName())) {
            employee.setName(dto.getName());
        }

        if (dto.getSurname() != null && !dto.getSurname().equals(employee.getSurname())) {
            employee.setSurname(dto.getSurname());
        }

        if (dto.getEnabled() != null && !dto.getEnabled().equals(employee.getEnabled())) {
            employee.setEnabled(dto.getEnabled());
        }

        if (dto.getPassword() != null && !dto.getPassword().equals(employee.getPassword())) {
            employee.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        }
    }
}
