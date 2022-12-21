package com.example.onlinewineshop.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.*;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class LoginController implements Initializable {
    @FXML
    private TextField tf_UserName;
    @FXML
    private Label fx_Nome;
    @FXML
    private Label fx_UserName;
    @FXML
    private TextField tf_Nome;
    @FXML
    private Button button_login;
    @FXML
    private RadioButton button_ChoiceC, button_ChoiceE;
    private int flag = 0;
    // faccio gestire la scelta del tipo di utente al controller
    @FXML
    public void getLogin(ActionEvent event) {
        if (button_ChoiceC.isSelected()) {
            fx_UserName.setText("UserName:");
            button_Signup.setDisable(false);
            fx_LabelSignUp.setDisable(false);
            tf_Nome.setDisable(true);
            tf_Nome.setVisible(false);
            fx_Nome.setDisable(true);
            fx_Nome.setVisible(false);
            flag = 0;
        } else if (button_ChoiceE.isSelected()) {
            button_Signup.setDisable(true);
            fx_LabelSignUp.setDisable(true);
            tf_Nome.setDisable(false);
            tf_Nome.setVisible(true);
            fx_Nome.setDisable(false);
            fx_Nome.setVisible(true);
            fx_UserName.setText("Cognome:");
            flag = 1;
        }
    }
    @FXML
    private Button button_Signup;
    @FXML
    private Button button_Logout;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private Label fx_LabelSignUp;
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        tf_Nome.setDisable(true);
        tf_Nome.setVisible(false);
        fx_Nome.setDisable(true);
        fx_Nome.setVisible(false);
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(flag==0){
                    DBUtilsClient.logInUser(event,tf_UserName.getText(),tf_password.getText()); // get the username and password

                }else{
                    DBUtilsEmployee.logInDip(event,tf_Nome.getText(),tf_UserName.getText(),tf_password.getText()); // get the username and password

                }
            }
        });
        button_Signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsClient.changeScene(event,"Sign up!","SignUp.fxml",null);
            }
        });

    }

}
