package com.example.onlinewineshop.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;



import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    private Button button_login;
    @FXML
    private Button button_Signup;
    @FXML
    private Button button_Logout;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.logInUser(event,tf_username.getText(),tf_password.getText()); // get the username and password
            }
        });
        button_Signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"Sign up!","SignUp.fxml",null);
            }
        });

    }

}
