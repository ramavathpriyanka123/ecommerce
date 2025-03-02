
package com.excelr.model;

import jakarta.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "electronics_id")
    private Electronics electronics;

    @ManyToOne
    @JoinColumn(name = "footwear_id")
    private Footwear footwear;

    @ManyToOne
    @JoinColumn(name = "grocery_id")
    private Grocery grocery;

    @ManyToOne
    @JoinColumn(name = "cosmetics_id")
    private Cosmetics cosmetics;

    @ManyToOne
    @JoinColumn(name = "laptops_id")
    private Laptops laptops;

    @ManyToOne
    @JoinColumn(name = "men_id")
    private MenClothing menClothing;

    @ManyToOne
    @JoinColumn(name = "women_id")
    private WomenClothing womenClothing;

    @ManyToOne
    @JoinColumn(name = "mobiles_id")
    private Mobiles mobiles;

    @ManyToOne
    @JoinColumn(name = "toys_id")
    private Toys toys;

    @ManyToOne
    @JoinColumn(name = "kids_id")
    private KidsClothing kidsClothing;

    private Integer qty;
    private Double price;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Electronics getElectronics() {
		return electronics;
	}
	public void setElectronics(Electronics electronics) {
		this.electronics = electronics;
	}
	public Footwear getFootwear() {
		return footwear;
	}
	public void setFootwear(Footwear footwear) {
		this.footwear = footwear;
	}
	public Grocery getGrocery() {
		return grocery;
	}
	public void setGrocery(Grocery grocery) {
		this.grocery = grocery;
	}
	public Cosmetics getCosmetics() {
		return cosmetics;
	}
	public void setCosmetics(Cosmetics cosmetics) {
		this.cosmetics = cosmetics;
	}
	public Laptops getLaptops() {
		return laptops;
	}
	public void setLaptops(Laptops laptops) {
		this.laptops = laptops;
	}
	public MenClothing getMenClothing() {
		return menClothing;
	}
	public void setMenClothing(MenClothing menClothing) {
		this.menClothing = menClothing;
	}
	public WomenClothing getWomenClothing() {
		return womenClothing;
	}
	public void setWomenClothing(WomenClothing womenClothing) {
		this.womenClothing = womenClothing;
	}
	public Mobiles getMobiles() {
		return mobiles;
	}
	public void setMobiles(Mobiles mobiles) {
		this.mobiles = mobiles;
	}
	public Toys getToys() {
		return toys;
	}
	public void setToys(Toys toys) {
		this.toys = toys;
	}
	public KidsClothing getKidsClothing() {
		return kidsClothing;
	}
	public void setKidsClothing(KidsClothing kidsClothing) {
		this.kidsClothing = kidsClothing;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "CartItem [id=" + id + ", cart=" + cart + ", electronics=" + electronics + ", footwear=" + footwear
				+ ", grocery=" + grocery + ", cosmetics=" + cosmetics + ", laptops=" + laptops + ", menClothing="
				+ menClothing + ", womenClothing=" + womenClothing + ", mobiles=" + mobiles + ", toys=" + toys
				+ ", kidsClothing=" + kidsClothing + ", qty=" + qty + ", price=" + price + "]";
	}
	public CartItem(Long id, Cart cart, Electronics electronics, Footwear footwear, Grocery grocery,
			Cosmetics cosmetics, Laptops laptops, MenClothing menClothing, WomenClothing womenClothing, Mobiles mobiles,
			Toys toys, KidsClothing kidsClothing, Integer qty, Double price) {
		super();
		this.id = id;
		this.cart = cart;
		this.electronics = electronics;
		this.footwear = footwear;
		this.grocery = grocery;
		this.cosmetics = cosmetics;
		this.laptops = laptops;
		this.menClothing = menClothing;
		this.womenClothing = womenClothing;
		this.mobiles = mobiles;
		this.toys = toys;
		this.kidsClothing = kidsClothing;
		this.qty = qty;
		this.price = price;
	}
	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}
}

