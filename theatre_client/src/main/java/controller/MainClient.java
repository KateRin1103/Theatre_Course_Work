package controller;

import client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainClient extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/start/Main.fxml"));
        primaryStage.setScene(new Scene(root));
        runStage(primaryStage);
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public static Scene getScene() {
        return primaryStage.getScene();
    }

    public static void runStage(Stage stage) throws IOException {

        primaryStage = stage;
        primaryStage.setTitle("Theatre");
        primaryStage.setResizable(false);
        primaryStage.setMaxHeight(1000);
        primaryStage.setMaxWidth(684);
        primaryStage.setMinHeight(1000);
        primaryStage.setMinWidth(684);
        Client.getInstanceClient();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
