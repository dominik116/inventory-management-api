package com.inventory.api.inventory_management.mapper;

import com.inventory.api.inventory_management.dto.EmployeeCreateDto;
import com.inventory.api.inventory_management.dto.EmployeeDto;
import com.inventory.api.inventory_management.dto.PagingDto;
import com.inventory.api.inventory_management.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(target = "id", ignore = true)
    Employee createDtoToEntity(EmployeeCreateDto dto);

    EmployeeDto entityToDto(Employee entity);

    default PagingDto<EmployeeDto> toPagingDto(Page<Employee> employees) {
        List<EmployeeDto> employeeDtos = employees.getContent().stream()
                .map(EmployeeMapper::toEmployeeDto)
                .collect(Collectors.toList());

        PagingDto<EmployeeDto> pagingDto = new PagingDto<>();
        pagingDto.setContent(employeeDtos);
        pagingDto.setPage(employees.getNumber());
        pagingDto.setSize(employees.getSize());
        pagingDto.setSort(employees.getSort().toString());
        pagingDto.setTotal((int) employees.getTotalElements());

        return pagingDto;
    }

    private static EmployeeDto toEmployeeDto(Employee employee) {
        final EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setUsername(employee.getUsername());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setNif(employee.getNif());
        dto.setPassword(employee.getPassword());

        return dto;
    }
}
