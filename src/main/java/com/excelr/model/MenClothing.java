package com.excelr.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "men")
public class MenClothing {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String category = "men";
    private String name;
    private String description;
    private Integer qty;
    private String image;
    private double price;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "MenClothing [id=" + id + ", name=" + name + ", description=" + description + ", qty=" + qty + ", image="
				+ image + ", price=" + price + "]";
	}
	public MenClothing(Long id, String name, String description, Integer qty, String image, double price) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.qty = qty;
		this.image = image;
		this.price = price;
	}
	public MenClothing() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
