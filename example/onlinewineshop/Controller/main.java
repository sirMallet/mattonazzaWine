package com.example.onlinewineshop.Controller;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Socket nel try
        try{
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("login.fxml"));
            primaryStage.setTitle("Online Wine Shop");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) throws IOException {
        launch(args);

    }
}

