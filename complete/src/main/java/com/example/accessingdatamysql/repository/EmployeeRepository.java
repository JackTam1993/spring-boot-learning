package com.example.accessingdatamysql.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.accessingdatamysql.entity.Employee;

import java.util.List;

@Transactional
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    List<Employee> findByLevel(Integer level);

    List<Employee> findByIsActive(Integer isActive);

    List<Employee> findByName(String name);
}
