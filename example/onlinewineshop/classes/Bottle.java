package com.example.onlinewineshop.classes;

public class Bottle extends Wine{
	private float quality;
	private double price;
	private int quantitySell;
	
	public Bottle(){
		super();
		this.quality = 0;
		this.price = 0;
		this.quantitySell = 0;
	}
	
	public Bottle(String nome, String region, int vintage, String varietal, int Valutazione,double prezzo, float quality, double price, int quantitySell){
		super(nome, region, vintage, varietal, Valutazione,prezzo);
		this.quality = quality;
		this.price = price;
		this.quantitySell = quantitySell;
	}
	
	public String toString(){
		return super.toString() + " Quality: " + quality + " Price: " + price + " Quantity sell: " + quantitySell;
	}
}
