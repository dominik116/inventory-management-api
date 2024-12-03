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

import java.net.URI;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class EmployeeController {

    private final EmployeeService employeeService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, path = "/employees", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<EmployeeDto> postEmployee(@Valid @RequestBody final EmployeeCreateDto dto) {
        return ResponseEntity.created(URI.create("/employees")).body(this.employeeService.create(dto));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/employees", produces = {"application/json"})
    public ResponseEntity<PagingDto<EmployeeDto>> findAllEmployeesWithPagination(
            @RequestParam(name = "page", defaultValue = "0") final int page, @RequestParam(name = "size", defaultValue = "10") final int size) {
        return ResponseEntity.ok(this.employeeService.findAll(page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/employees/{id}", produces = {"application/json"})
    public ResponseEntity<EmployeeDto> findEmployeeById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(this.employeeService.findById(id));
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
