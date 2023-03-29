package com.maxtrain.bootcamp.sales.employee;

import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin 
@RestController 
@RequestMapping("/api/employees") 

public class EmployeeController {
		
	@Autowired 
	private EmployeeRepository emplRepo;
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	@GetMapping("{email}/{password}")
	public ResponseEntity<Employee> loginEmployee(@PathVariable String email, @PathVariable String password) {
		Optional<Employee> employee = emplRepo.findByEmailAndPassword(email, password);
		if(employee.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Employee>(employee.get(), HttpStatus.OK);
	}
	
	@GetMapping 
	public ResponseEntity<Iterable<Employee>> getEmployees() { 
		Iterable<Employee> employees = emplRepo.findAll();
		return new ResponseEntity<Iterable<Employee>>(employees, HttpStatus.OK); 
	} 
	
	@GetMapping("{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable int id) {
		Optional<Employee> employee = emplRepo.findById(id);
		if(employee.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Employee>(employee.get(), HttpStatus.OK);
	}

	
	@GetMapping("email/{email}")
	public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
		Optional<Employee> employee = emplRepo.findByEmail(email);
		if(employee.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Employee>(employee.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Employee> postEmployee (@RequestBody Employee employee) {
		Employee newEmployee = emplRepo.save(employee);
		return new ResponseEntity<Employee>(newEmployee, HttpStatus.CREATED);
		
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putEmployee (@PathVariable int id, @RequestBody Employee employee) {
		if(employee.getId() != id) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		emplRepo.save(employee);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
		
	}


	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteEmployee(@PathVariable int id) {
		Optional<Employee> employee = emplRepo.findById(id);
		if(employee.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		emplRepo.delete(employee.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}

