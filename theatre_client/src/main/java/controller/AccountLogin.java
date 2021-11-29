package controller;

import client.Client;
import controller.admin.InteractionWithProgInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import static client.Client.adminCheck;
import static client.Client.userCheck;

public class AccountLogin extends InteractionWithProgInterface {
    public static String enteredUserLogin;
    public TextField adminLogin;
    public PasswordField adminPassword;

    public String getLogin() {
        return adminLogin.getText();
    }

    public String getPassword() {
        return adminPassword.getText();
    }

    public void AdminAuthorization(ActionEvent actionEvent) throws IOException {
        if (getLogin().isEmpty() || getPassword().isEmpty())
            showAlertEmpty();
        boolean flag = adminCheck(getLogin(), getPassword());
        if (flag) {
            Sample.windowStart.close();
            Sample.windowLogin.close();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/AdminMenu.fxml"));
            MainClient.primaryStage.setScene(new Scene(root));
            MainClient.primaryStage.show();
        } else {
            adminLogin.clear();
            adminPassword.clear();
            showAlert();
        }
    }

    public void enterUser(ActionEvent actionEvent) throws IOException {
        boolean flag = userCheck(getLogin(), getPassword());
        if (flag) {
            enteredUserLogin = getLogin();
            Sample.windowStart.close();
            Sample.windowLogin.close();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/UserMenu.fxml"));
            MainClient.primaryStage.setScene(new Scene(root));
            MainClient.primaryStage.show();
        } else {
            adminLogin.clear();
            adminPassword.clear();
            showAlertU();
        }
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ОШИБКА");
        alert.setHeaderText("Справка");
        alert.setContentText("Администратора с такими данными нет! Повторите ввод!");
        alert.showAndWait();
    }

    public void showAlertEmpty() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Справка");
        alert.setContentText("Заполните все поля!");
        alert.showAndWait();
    }

    public void showAlertU() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Справка");
        alert.setContentText("Пользователя с такими данными нет! Повторите ввод!");
        alert.showAndWait();
    }

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/start/Main.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toEditFilm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/RedactFilm.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toShowBookings(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/ShowBookings.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toDelBookings(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/DeleteBooking.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toDelBookingsUser(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/userActions/DeleteBooking.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toShowNotificationsForUser(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/userActions/ShowNotifications.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toAddRating(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/userActions/AddRating.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toShowRatings(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/userActions/ShowRatings.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toDeleteSeance(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/RedactSeances.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toRedactUser(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/RedactUser.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toBuyProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/userActions/BookTickets.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toBarChart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/ShowStatistics.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toNotification(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/ShowNotifications.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toDamages(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/ShowDamages.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toAvgRating(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/ShowAvgRatings.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toRating(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/ShowRatings.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void makeAccReport(ActionEvent actionEvent) {
        Client.getReport();
        successReport();
    }

    public void successReport() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Отчёт составлен!");
        alert.showAndWait();
    }

}