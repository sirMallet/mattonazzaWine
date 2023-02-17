package com.example.onlinewineshop.Controller;

public class UserInformation {
    // variabili
    private String username;
    private ClientSession clientSession;
    //costruttore
    public UserInformation(String username, ClientSession clientSession) {
        this.username = username;

        this.clientSession = clientSession;
    }
    // getter e setter
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public ClientSession getClientSession() {
        return clientSession;
    }
    }
