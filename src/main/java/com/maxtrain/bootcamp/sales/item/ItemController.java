package com.maxtrain.bootcamp.sales.item;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin // same thing as CORS in C#
@RestController // send and receive JSON
@RequestMapping("/api/items") // this points to controllers 
public class ItemController {
	
	@Autowired // prop of custRepo will get filled with an instance of CustomerRepository
	private ItemRepository iteRepo;
	
	
	@GetMapping // this is like HttpGet
	public ResponseEntity<Iterable<Item>> getItems() { // generic class returns collection of customers
		Iterable<Item> items = iteRepo.findAll();
		return new ResponseEntity<Iterable<Item>>(items, HttpStatus.OK); // returns execution message
		
	} 
	
	@GetMapping("{id}")
	public ResponseEntity<Item> getItem(@PathVariable int id) {
		Optional<Item> item = iteRepo.findById(id);
		if(item.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Item>(item.get(), HttpStatus.OK);
	}

	
	@PostMapping
	public ResponseEntity<Item> postCustomer (@RequestBody Item item) {
		Item newItem = iteRepo.save(item);
		return new ResponseEntity<Item>(newItem, HttpStatus.CREATED);
		
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putItem (@PathVariable int id, @RequestBody Item item) {
		if(item.getId() != id) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		iteRepo.save(item);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
		
	}


	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteItem(@PathVariable int id) {
		Optional<Item> item = iteRepo.findById(id);
		if(item.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		iteRepo.delete(item.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
