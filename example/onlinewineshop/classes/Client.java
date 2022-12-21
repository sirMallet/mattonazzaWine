package com.example.onlinewineshop.classes;

/*
 * Cliente (non registrato):
 ! - registrarsi con nome utente e password + altre informazioni
 ! - effettuare il login (per poter acquistare la bottiglia di vino)
 ! - ricercare i vini (per nome, anno)
 ! - visualizza vini in vendita, definire quantità
 */
public class Client{
	private String userName;
	private String password;
	private String name;
	private String surname;
	private String fiscalCode;
	private String phone;

	private String address;
	private String email;
	//costruttore
	public Client(String userName, String password, String name, String surname, String fiscalCode, String phone, String address, String email) {
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.fiscalCode = fiscalCode;
		this.phone = phone;
		this.address = address;
		this.email = email;
	}
	public Client(){
		this.userName = "";
		this.password = "";
		this.name = "";
		this.surname = "";
		this.fiscalCode = "";
		this.phone = "";
		this.address = "";
		this.email = "";

	}
	//getter e setter
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getFiscalCode() {
		return fiscalCode;
	}
	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
