package com.example.onlinewineshop.classes;

/*
 * Dipendente:
 ! - accede al sistema tramite nome utente e password
 ! - modifcare password
 ! - ricerca clienti
 ! - ricerca vini
 ! - ricerca ordini vendita/acquisto
 ! - ricerca proposte
 */

public class Employee {
	private String name;
	private String surname;
	private String fiscalCode;
	private String email;
	private int telephone;
	private String address;
	
	public Employee() {
		this.name = "";
		this.surname = "";
		this.fiscalCode = "";
		this.email = "";
		this.telephone = 0;
		this.address = "";
	}
	
	public Employee(String name, String surname, String fiscalCode, String email, int telephone, String address) {
		this.name = name;
		this.surname = surname;
		this.fiscalCode = fiscalCode;
		this.email = email;
		this.telephone = telephone;
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString(){
		return "Name: " + name + " Surname: " + surname + " Fiscal code: " + fiscalCode + " Email: " + email + " Phone: " + telephone + " Address: " + address;
	}
}
