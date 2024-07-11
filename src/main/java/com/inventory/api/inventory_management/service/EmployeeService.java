package com.inventory.api.inventory_management.service;

import com.inventory.api.inventory_management.dto.EmployeeCreateDto;
import com.inventory.api.inventory_management.dto.EmployeeDto;
import com.inventory.api.inventory_management.dto.PagingDto;
import com.inventory.api.inventory_management.entity.Employee;
import com.inventory.api.inventory_management.mapper.EmployeeMapper;
import com.inventory.api.inventory_management.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository repository;

    private final EmployeeMapper mapper;

    private final PasswordEncoder passwordEncoder;

    public EmployeeDto create(final EmployeeCreateDto dto) {
        log.info("IN EmployeeService: create");
        return this.mapper.entityToDto(this.repository.save(this.mapper.createDtoToEntity(dto)));
    }

    public EmployeeDto findById(final Long id) {
        log.info("IN EmployeeService: findById");
        return this.mapper.entityToDto(this.repository.findById(id).orElseThrow(EntityNotFoundException::new));
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
        log.info("OUT EmployeeService: putUpdate");
    }

    public void deleteById(final Long id) {
        log.info("IN EmployeeService: deleteById");
        this.repository.deleteById(id);
        log.info("OUT EmployeeService: deleteById");
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

        if (dto.getPassword() != null && !dto.getPassword().equals(employee.getPassword())) {
            employee.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        }
    }
}
