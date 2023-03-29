package com.maxtrain.bootcamp.sales.customer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin // same thing as CORS in C#
@RestController // send and receive JSON
@RequestMapping("/api/customers") // this points to controllers 
public class CustomerController {
	
		@Autowired // prop of custRepo will get filled with an instance of CustomerRepository
		private CustomerRepository custRepo;
		
		
		@GetMapping // this is like HttpGet
		public ResponseEntity<Iterable<Customer>> getCustomers() { // generic class returns collection of customers
			Iterable<Customer> customers = custRepo.findAll();
			return new ResponseEntity<Iterable<Customer>>(customers, HttpStatus.OK); // returns execution message
			
		} 
		
		@GetMapping("{id}")
		public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
			Optional<Customer> customer = custRepo.findById(id);
			if(customer.isEmpty() ) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
		}

		
		@GetMapping("code/{code}")
		public ResponseEntity<Customer> getCustomerByCode(@PathVariable String code) {
			Optional<Customer> customer = custRepo.findByCode(code);
			if(customer.isEmpty() ) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
		}
		
		@PostMapping
		public ResponseEntity<Customer> postCustomer (@RequestBody Customer customer) {
			Customer newCustomer = custRepo.save(customer);
			return new ResponseEntity<Customer>(newCustomer, HttpStatus.CREATED);
			
		}

		@SuppressWarnings("rawtypes")
		@PutMapping("{id}")
		public ResponseEntity putCustomer (@PathVariable int id, @RequestBody Customer customer) {
			if(customer.getId() != id) {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
			custRepo.save(customer);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			
		}


		@SuppressWarnings("rawtypes")
		@DeleteMapping("{id}")
		public ResponseEntity deleteCustomer(@PathVariable int id) {
			Optional<Customer> customer = custRepo.findById(id);
			if(customer.isEmpty()) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			
			custRepo.delete(customer.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
}

