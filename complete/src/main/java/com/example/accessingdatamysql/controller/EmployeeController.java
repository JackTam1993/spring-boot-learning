package com.example.accessingdatamysql.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.accessingdatamysql.entity.Employee;
import com.example.accessingdatamysql.repository.EmployeeRepository;

@Controller
@RequestMapping(path = "/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping(path = "/save")
    public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam Integer level) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Boolean isNameExist = employeeRepository.findByName(name).size() == 0;

        if(isNameExist.equals(true)) {
            try {
                Employee employee = Employee
                    .builder()
                    .name(name)
                    .level(level)
                    .isActive(1)
                    .build();
                employeeRepository.save(employee);
                return "Saved";
            } catch (Exception e) {
                System.out.print(e);
            }
            return "Saved";
        } else {
            return "User already exist";
        }
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Employee> getAllEmployee () {
        return employeeRepository.findByIsActive(1);
    }

    @GetMapping(path = "/employeeList/{level}")
    public @ResponseBody Iterable<Employee> getEmployeeList (@PathVariable("level") Integer level) {
        return employeeRepository.findByLevel(level);
    }

    @PostMapping(path = "/updateLevel")
    public @ResponseBody String updateEmployee(@RequestParam Integer id, @RequestParam Integer level) {
        
        try {
            Boolean isEmployeeExist = employeeRepository.existsById(id);
            if(isEmployeeExist.equals(true)) {
                Employee employee = employeeRepository.findById(id).get();
                employee.setLevel(level);
                employeeRepository.save(employee);
            } else {
                return "no such a employee";
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return "updated successfully"; 
    }

    @PostMapping(path = "/delete")
    public @ResponseBody String deleteEmployee(@RequestParam Integer id) {
        
        try {
            Boolean isEmployeeExist = employeeRepository.existsById(id);
            if(isEmployeeExist.equals(true)) {
                // employeeRepository.deleteById(id);
                Employee employee = employeeRepository.findById(id).get();
                employee.setIsActive(0);
                
                employeeRepository.save(employee);
            } else {
                return "no such a employee";
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return "Deleted"; 
    }
}
