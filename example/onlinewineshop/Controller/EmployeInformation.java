package com.example.onlinewineshop.Controller;

public class EmployeInformation {
    // variabili
    private String cognome;
    private String nome;
    private EmployeSession employeSession;
    private int Admin;

    //costruttore
    public EmployeInformation(String nome,String cognome, EmployeSession employeSession, int Admin) {
        this.cognome = cognome;
        this.nome = nome;
        this.Admin = Admin;
        this.employeSession = employeSession;
    }

    // getter e setter
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getAdmin() {
        return Admin;
    }

    public void setAdmin(int admin) {
        Admin = admin;
    }

    public EmployeSession getEmployeSession() {
        return employeSession;
    }

    public void setEmployeSession(EmployeSession employeSession) {
        this.employeSession = employeSession;
    }



}