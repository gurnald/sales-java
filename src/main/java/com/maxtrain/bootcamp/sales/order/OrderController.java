package com.maxtrain.bootcamp.sales.order;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maxtrain.bootcamp.sales.customer.Customer;
@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	private final String Status_NEW = "NEW";
	private final String Status_Review = "REVIEW";
	private final String Status_Approved = "APPROVED";
	private final String Status_Rejected = "REJECTED";

	@Autowired
	private OrderRepository ordRepo;
	
	
	@GetMapping("reviews")
	public ResponseEntity<Iterable<Order>> getOrdersInReviews() {
		Iterable<Order> ordersInReview = ordRepo.findByStatus(Status_Review);
		return new ResponseEntity<Iterable<Order>>(ordersInReview, HttpStatus.OK);
	}
	

	@GetMapping // this is like HttpGet
	public ResponseEntity<Iterable<Order>> getOrders() { // generic class returns collection of customers
		Iterable<Order> orders = ordRepo.findAll();
		return new ResponseEntity<Iterable<Order>>(orders, HttpStatus.OK); // returns execution message

	}	
	
	@GetMapping("{id}")
	public ResponseEntity<Order> getOrder(@PathVariable int id) {
		Optional<Order> order = ordRepo.findById(id);
		if(order.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Order>(order.get(), HttpStatus.OK);
	}
	
	
	
	@PostMapping
	public ResponseEntity<Order> postOrder (@RequestBody Order order) {
		Order newOrder = ordRepo.save(order);
		return new ResponseEntity<Order>(newOrder, HttpStatus.CREATED);
		
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putOrder (@PathVariable int id, @RequestBody Order order) {
		if(order.getId() != id) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		ordRepo.save(order);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
		
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("review/{id}")
	public ResponseEntity reviewOrder(@PathVariable int id, @RequestBody Order order) {
		String newStatus = order.getTotal() <= 100 ? Status_Approved : Status_Review;
		order.setStatus(newStatus);
		return putOrder(id, order);
		
	}
	
	
	@SuppressWarnings("rawtypes")
	@PutMapping("approve/{id}")
	public ResponseEntity approveOrder(@PathVariable int id, @RequestBody Order order) {
		String newStatus = (Status_Approved);
		order.setStatus(newStatus);
		return putOrder(id, order);
		
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("reject/{id}")
	public ResponseEntity rejectOrder(@PathVariable int id, @RequestBody Order order) {
		String newStatus = (Status_Rejected);
		order.setStatus(newStatus);
		return putOrder(id, order);
		
	}
	
	

	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteOrder(@PathVariable int id) {
		Optional<Order> order = ordRepo.findById(id);
		if(order.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		ordRepo.delete(order.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
