package com.inventory.api.inventory_management.controller;

import com.inventory.api.inventory_management.dto.EmployeeCreateDto;
import com.inventory.api.inventory_management.dto.EmployeeDto;
import com.inventory.api.inventory_management.dto.PagingDto;
import com.inventory.api.inventory_management.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class EmployeeController {

    private final EmployeeService employeeService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/employees", produces = {"application/json"})
    public ResponseEntity<PagingDto<EmployeeDto>> findAllEmployeesWithPagination(
            @RequestParam(name = "page", defaultValue = "0") final int page, @RequestParam(name = "size", defaultValue = "10") final int size) {
        return ResponseEntity.ok(this.employeeService.findAll(page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/employees/{username}", produces = {"application/json"})
    public ResponseEntity<EmployeeDto> findEmployeeByUsername(@PathVariable("username") final String username) {
        return ResponseEntity.ok(this.employeeService.findByUsername(username));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, path = "/employees/{id}", produces = {"application/json"})
    public void putUpdateEmployee(@PathVariable("id") final Long id, @Valid @RequestBody final EmployeeCreateDto dto) {
        this.employeeService.putUpdate(id, dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, path = "/employees/{id}", produces = {"application/json"})
    public void deleteEmployeeById(@PathVariable("id") final Long id) {
        this.employeeService.deleteById(id);
    }
}
