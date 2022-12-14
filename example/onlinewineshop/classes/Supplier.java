package com.example.onlinewineshop.classes;

/*
 * Fornitore:
 ! - prepara ordine
 ! - invia ordine al magazzino
 */

public class Supplier{
	private String name;
	private String surname;
	private String company;
	private String fiscalCode;
	private String email;
	
	public Supplier(){
		this.name = "";
		this.surname = "";
		this.company = "";
		this.fiscalCode = "";
		this.email = "";
	}
	
	public Supplier(String name, String surname, String company, String fiscalCode, String email){
		this.name = name;
		this.surname = surname;
		this.company = company;
		this.fiscalCode = fiscalCode;
		this.email = email;
	}
	
	public String toString(){
		return "Name: " + name + " Surname: " + surname + " Company: " + company + " Fiscal code: " + fiscalCode + " Email: " + email;
	}
}
