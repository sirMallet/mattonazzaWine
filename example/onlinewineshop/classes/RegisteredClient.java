package com.example.onlinewineshop.classes;

/*
 * Cliente registrato:
 ! - acquisto bottiglia (1/5 bottiglie e casse da 6/12 bottiglie)
 ! - pagamento (carta di credito, bonifico)
 ! - visualizza ordini
 ? - visualizza informazioni personali
 */

public class RegisteredClient extends Client{
	
	public RegisteredClient(){
		super();
	}
	public RegisteredClient(String name, String surname, String fiscalCode, String phone, String address, String email) {
		super(name, surname, fiscalCode, phone, address, email);
	}
	
	public String toString(){
		return super.toString();
	}
}
