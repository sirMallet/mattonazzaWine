package com.example.onlinewineshop.classes;

/*
 * Cliente (non registrato):
 ! - registrarsi con nome utente e password + altre informazioni
 ! - effettuare il login (per poter acquistare la bottiglia di vino)
 ! - ricercare i vini (per nome, anno)
 ! - visualizza vini in vendita, definire quantità
 */
public class Client{
	private String name;
	private String surname;
	private String fiscalCode;
	private String phone;
	private String address;
	private String email;
	// costruttore
	public Client(){}
	
	public Client(String name, String surname, String fiscalCode, String phone, String address, String email){
		super();
		this.name = name;
		this.surname = surname;
		this.fiscalCode = fiscalCode;
		this.phone = phone;
		this.address = address;
		this.email = email;
	}
	// getter
	public String getName(){
		return name;
	}
	public String getSurname(){
		return surname;
	}
	public String getFiscalCode(){
		return fiscalCode;
	}
	public String getPhone(){
		return phone;
	}
	public String getAddress(){
		return address;
	}
	public String getEmail(){
		return email;
	}
	// setter
	public void setName(String name){
		this.name = name;
	}
	public void setSurname(String cognome){
		this.surname = surname;
	}
	public void setFiscalCode(String CF){
		this.fiscalCode = fiscalCode;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	public void setAddress(String Address){
		this.address = Address;
	}
	public void setEmail(String email){
		this.email = email;
	}
	// toString
	@Override
	public String toString(){
		return "Client [Name = " + name + ", Surname = " + surname + ", Fiscal code = " + fiscalCode + ", Phone = " + phone + ", Address = " + address + ", email = " + email + "]";
	}
}
