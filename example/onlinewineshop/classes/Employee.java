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
	private String Nome;
	private String Cognome;
	private String FiscalCode;
	private String IndirizzoEmail;
	private String NumTel;
	private String IndirizzoResidenza;
	private Boolean isAdmin;
	private String Password;
	
	public Employee() {
		this.Nome = "";
		this.Cognome = "";
		this.FiscalCode = "";
		this.IndirizzoEmail = "";
		this.NumTel = "";
		this.IndirizzoResidenza = "";
		this.isAdmin = false;
		this.Password = "";

	}
	
	//Costruttore
	public Employee(String nome, String cognome, String fiscalCode, String indirizzoEmail, String numeroTel, String indirizzoResidenza, Boolean isAdmin, String password) {
		this.Nome = nome;
		this.Cognome = cognome;
		this.FiscalCode = fiscalCode;
		this.IndirizzoEmail = indirizzoEmail;
		this.NumTel = numeroTel;
		this.IndirizzoResidenza = indirizzoResidenza;
		this.isAdmin = isAdmin;
		this.Password = password;
	}
	//getter e setter
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public String getCognome() {
		return Cognome;
	}
	public void setCognome(String cognome) {
		Cognome = cognome;
	}
	public String getFiscalCode() {
		return FiscalCode;
	}
	public void setFiscalCode(String fiscalCode) {
		FiscalCode = fiscalCode;
	}
	public String getIndirizzoEmail() {
		return IndirizzoEmail;
	}
	public void setIndirizzoEmail(String indirizzoEmail) {
		IndirizzoEmail = indirizzoEmail;
	}
	public String getNumTel() {
		return NumTel;
	}
	public void setNumTel(String numeroTel) {
		NumTel = numeroTel;
	}
	public String getIndirizzoResidenza() {
		return IndirizzoResidenza;
	}
	public void setIndirizzoResidenza(String indirizzoResidenza) {
		IndirizzoResidenza = indirizzoResidenza;
	}
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getPassword() {
		return Password;
	}
public void setPassword(String password) {
		Password = password;
	}
}
