package com.example.onlinewineshop.Controller;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
public class main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Online Wine Shop");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

