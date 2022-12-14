package com.example.onlinewineshop.classes;

/*
 * Amministratore:
 ! - ha una password iniziale
 ! - registra gli altri dipendenti
 ! - modifica dipendenti (cancellazione, modifica password)
 ! - creare report mensili
 *
 */

public class Admin extends Employee {
	
	public Admin() {
		super();
	}
	
	public Admin(String name, String surname, String fiscalCode, String email, int telephone, String address) {
		super(name, surname, fiscalCode, email, telephone, address);
	}
	
	public String toString(){
		return super.toString();
	}
}
