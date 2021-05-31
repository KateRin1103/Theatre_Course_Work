package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Sample extends InteractionWithProgInterface {

    public static  Stage windowStart;

    public static  Stage windowLogin;

    public static  Stage windowRegistration;

    public static  Stage windowRedact;

    public void toAdminLogin (ActionEvent actionEvent) throws IOException {
        windowLogin = new Stage();
        windowLogin.setTitle("Авторизация админа");
        windowLogin.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/AdminLogin.fxml"));
        Scene scene = new Scene(root);
        windowLogin.setScene(scene);
        windowLogin.show();
    }

    public void toUserLogin (ActionEvent actionEvent) throws IOException {
        windowLogin = new Stage();
        windowLogin.setTitle("User authorization");
        windowLogin.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/UserLogin.fxml"));
        Scene scene = new Scene(root);
        windowLogin.setScene(scene);
        windowLogin.showAndWait();
    }

    public void toStart (ActionEvent actionEvent) throws IOException {
        windowStart = new Stage();
        windowStart.setTitle("Authorization");
        windowStart.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/start/Start.fxml"));
        Scene scene = new Scene(root);
        windowStart.setScene(scene);
        windowStart.show();
    }

    public void toInformation (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/start/Information.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toMain (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/start/Main.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toRegistration (ActionEvent actionEvent) throws IOException {
        windowRegistration = new Stage();
        windowRegistration.setTitle("Регистрация");
        windowRegistration.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/registration/Registration.fxml"));
        Scene scene = new Scene(root);
        windowRegistration.setScene(scene);
        windowRegistration.showAndWait();
    }

}

