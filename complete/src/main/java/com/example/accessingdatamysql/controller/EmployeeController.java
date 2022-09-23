package com.example.accessingdatamysql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.accessingdatamysql.common.ErrorCodes;
import com.example.accessingdatamysql.common.Response;
import com.example.accessingdatamysql.entity.Employee;
import com.example.accessingdatamysql.repository.EmployeeRepository;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping(path = "/save")
    public Response addNewUser (@RequestParam String name, @RequestParam Integer level) {
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
                return Response.ok();
            } catch (Exception e) {
                System.out.print(e);
                return Response.fail(ErrorCodes.USER_EXIST);
            }
        } else {
            return Response.fail(ErrorCodes.USER_EXIST);
        }
    }

    @GetMapping(path = "/all")
    public @ResponseBody Response getAllEmployee () {
        return Response.ok(employeeRepository.findByIsActive(1));
    }

    @GetMapping(path = "/employeeList/{level}")
    public @ResponseBody Iterable<Employee> getEmployeeList (@PathVariable("level") Integer level) {
        return employeeRepository.findByLevel(level);
    }

    @PostMapping(path = "/updateLevel")
    public @ResponseBody Response updateEmployee(@RequestParam Integer id, @RequestParam Integer level) {
        
        try {
            Boolean isEmployeeExist = employeeRepository.existsById(id);
            if(isEmployeeExist.equals(true)) {
                Employee employee = employeeRepository.findById(id).get();
                employee.setLevel(level);
                employeeRepository.save(employee);
                return Response.ok(); 
            } else {
                return Response.fail(ErrorCodes.NO_SUCH_USER);
            }
        } catch (Exception e) {
            System.out.print(e);
            return Response.fail(ErrorCodes.NO_SUCH_USER);
        }
    }

    @PostMapping(path = "/delete")
    public @ResponseBody Response deleteEmployee(@RequestParam Integer id) {
        
        try {
            Boolean isEmployeeExist = employeeRepository.existsById(id);
            if(isEmployeeExist.equals(true)) {
                // employeeRepository.deleteById(id);
                Employee employee = employeeRepository.findById(id).get();
                employee.setIsActive(0);
                
                employeeRepository.save(employee);
                return Response.ok();
            } else {
                return Response.fail(ErrorCodes.NO_SUCH_USER);
            }
        } catch (Exception e) {
            System.out.print(e);
            return Response.fail(ErrorCodes.NO_SUCH_USER);
        }
    }
}
