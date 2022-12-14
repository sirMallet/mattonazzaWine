package com.example.onlinewineshop.Controller;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import javafx.scene.Scene;
public class signupController implements Initializable {
    @FXML
    private Button button_Signup;
    @FXML
    private Button button_Login;
    @FXML
    private TextField ft_username;
    @FXML
    private TextField ft_Nome;
    @FXML
    private TextField ft_Cognome;
    @FXML
    private TextField ft_Indirizzo;
    @FXML
    private TextField ft_NumCell;
    @FXML
    private TextField ft_Email;
    @FXML
    private TextField ft_CF;

    @FXML
    private TextField getFt_NumCell;
    @FXML
    private TextField ft_password;
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        button_Signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (!ft_username.getText().isEmpty() && !ft_password.getText().isEmpty() && !ft_Nome.getText().isEmpty()&& !ft_Cognome.getText().isEmpty()&& !ft_Email.getText().isEmpty()&& !ft_CF.getText().isEmpty()&& !ft_NumCell.getText().isEmpty()&& !ft_Indirizzo.getText().isEmpty()) {
                    DBUtils.signUpUsers(event, ft_username.getText(), ft_password.getText(),ft_Nome.getText(), ft_Cognome.getText(),ft_CF.getText(), ft_NumCell.getText(),ft_Email.getText(), ft_Indirizzo.getText());
                }
                else{
                    System.out.println("Please fill in all the fields!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("errore di inserimento");
                    alert.show();
                }
            }
        });
        button_Login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"Login in!","login.fxml",null);
            }
        });
    }
}
