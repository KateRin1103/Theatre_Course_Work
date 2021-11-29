package controller;

import account.Account;
import account.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import static client.Client.*;
import static controller.alerts.UserAlerst.*;

public class Registration extends InteractionWithProgInterface {

    public PasswordField userPassword;
    public TextField userLogin;
    public TextField userSurname;
    public TextField userName;
    public TextField userPhone;
    public String getLogin() {
        return userLogin.getText();
    }

    public void addNewUser(ActionEvent actionEvent) {
        if (userLogin.getText().isEmpty() || userPassword.getText().isEmpty()
                || userName.getText().isEmpty() || userSurname.getText().isEmpty() || userPhone.getText().isEmpty())
            showAlertEmpty();
        else if (checkSameUser(getLogin())) {
            userLogin.clear();
            showAlertSameLogin();
        } else if (!checkCorrectAccount(userName.getText(), userSurname.getText())) {
            userName.clear();
            userSurname.clear();
            showAlertIncorrect();
        } else {
            addNewAccountUser(new User(userName.getText(), userSurname.getText(),
                    userPhone.getText(), new Account(userLogin.getText(), userPassword.getText())));
            clearAllFields();
            showAlertSuccess();
        }
    }

    public void clearAllFields() {
        userLogin.clear();
        userPassword.clear();
        userSurname.clear();
        userName.clear();
    }

    public void addNewRegUser(ActionEvent actionEvent) throws IOException {
        addNewUser(new ActionEvent());
        Sample.windowRegistration.close();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/accountLogin/UserLogin.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }
}
