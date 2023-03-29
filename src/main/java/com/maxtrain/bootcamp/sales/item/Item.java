package com.maxtrain.bootcamp.sales.item;

import jakarta.persistence.*;

@Entity
@Table(name="items")

public class Item {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=30, nullable=false)
	private String Name;
	@Column(columnDefinition="decimal(11,2) NOT NULL DEFAULT 0")
	private double Price;
	
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		Price = price;
	}
	


}
