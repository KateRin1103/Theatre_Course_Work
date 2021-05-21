package controller;

import account.Account;
import account.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import theatre.Seance;
import theatre.Spectacle;

import java.io.IOException;
import java.time.LocalTime;

import static client.Client.*;
import static java.lang.Integer.parseInt;

public class Registration {
    public PasswordField userPassword;
    public TextField userLogin;
    public TextField userSurname;
    public TextField userName;
    public TextField userPhone;

    public TextField titleReg;
    public TextField durationReg;

    public ComboBox<String> titleChoose;
    public Label label;
    public ComboBox hourChoose;
    public ComboBox minChoose;
    public ComboBox price;
    public DatePicker seanceDate;

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

    public void showAlertEmpty() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ОШИБКА");
        alert.setHeaderText("Справка");
        alert.setContentText("Заполните все поля!");
        alert.showAndWait();
    }

    public void showAlertSameLogin() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ОШИБКА");
        alert.setHeaderText("Справка");
        alert.setContentText("Учётная запись с таким логином уже существует!");
        alert.showAndWait();
    }

    public void showAlertIncorrect() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ОШИБКА");
        alert.setHeaderText("Справка");
        alert.setContentText("Введите корректно имя и фамилию!");
        alert.showAndWait();
    }

    public void showAlertSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Поздравляем");
        alert.setHeaderText(null);
        alert.setContentText("Запись успешно добавлена!");
        alert.showAndWait();
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

    public void toMainAdmin(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/AdminMenu.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void addNewSpectacles(ActionEvent actionEvent) {
        if (titleReg.getText().isEmpty() || durationReg.getText().isEmpty())
            showAlertEmpty();
        else if (checkCorrectInt(durationReg.getText())) {
            durationReg.clear();
            showAlertEmptySpect();
        } else {
            try {
                int dur = parseInt(durationReg.getText());
                addNewSpectacle(new Spectacle(titleReg.getText(), dur));
                showAlertSuccess();
            } catch (NumberFormatException ex) {
                showAlertEmptySpect();
            }
            titleReg.clear();
            durationReg.clear();
        }
    }

    public void updateList(ActionEvent actionEvent) throws IOException {
        ObservableList<String> langs = FXCollections.observableArrayList(getSpectacleTitles());
        titleChoose.setItems(langs);
        String s = titleChoose.getSelectionModel().toString();
        label.setText(s);
    }

    public void addNewSeance(ActionEvent actionEvent) {
        if (titleChoose.getValue() == null || seanceDate.getValue() == null
                || hourChoose.getValue() == null || minChoose.getValue() == null
                || price.getValue() == null) {
            showAlertEmptySpect();
        } else {
            try {
                addNewSeances(new Seance(
                        titleChoose.getValue(),
                        seanceDate.getValue(),
                        LocalTime.of(parseInt(hourChoose.getValue().toString()),
                                parseInt(minChoose.getValue().toString()), 0),
                        parseInt(String.valueOf(price.getValue()))));
                showAlertSuccess();
            } catch (NumberFormatException ex) {
                showAlertEmptySpect();
            }
        }
        titleChoose.setValue(null);
        seanceDate.setValue(null);
        hourChoose.setValue(null);
        minChoose.setValue(null);
        price.setValue(null);
    }

    public void showAlertEmptySpect() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ОШИБКА");
        alert.setHeaderText("Справка");
        alert.setContentText("Значения неверны!");
        alert.showAndWait();
    }

    public void toMain (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/start/Main.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }


    public static boolean checkCorrectInt(String str) {
        for (var i = 0; i < str.length(); i++) {
            if (!(str.charAt(0) >= 'А' && str.charAt(0) <= 'Я'))
                return false;
            if (!(str.charAt(i) >= 'а' && str.charAt(i) <= 'я')
                    && !(str.charAt(i) >= 'А' && str.charAt(i) <= 'Я')
                    && !(str.charAt(i) == ' ') && !(str.charAt(i) == '-'))
                return false;
            if (!(str.charAt(0) >= 'A' && str.charAt(0) <= 'Z'))
                return false;
            if (!(str.charAt(i) >= 'a' && str.charAt(i) <= 'z')
                    && !(str.charAt(i) >= 'А' && str.charAt(i) <= 'Я'))
                return false;
        }
        return true;
    }

}
