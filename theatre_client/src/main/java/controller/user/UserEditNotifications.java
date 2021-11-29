package controller.user;

import controller.MainClient;
import controller.UserInteractionWithProgInterface;
import controller.alerts.AdminAlerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import statistics.Notification;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static client.Client.deleteSelectedNotification;
import static client.Client.getAllNotifications;

public class UserEditNotifications extends UserInteractionWithProgInterface implements Initializable {

    public TableView<Notification> Notification;
    public TableColumn<Notification, String> login;
    public TableColumn<Notification, String> notification;
    public TableColumn<Notification, LocalDate> time;
    public TableColumn<Notification, LocalTime> date;

    public TextField filterField = new TextField();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Notification> nSeancesDel = null;
        try {
            nSeancesDel = FXCollections.observableArrayList(getAllNotifications());
        } catch (IOException e) {
            e.printStackTrace();
        }

        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        notification.setCellValueFactory(new PropertyValueFactory<>("changed"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        Notification.setItems(nSeancesDel);

        searchSeances();
        Notification.setEditable(true);
    }

    public void delBooking(ActionEvent actionEvent) throws IOException {
        Notification selected = Notification.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AdminAlerts.showAlertNoSelected("Выберете уведомление!");
        } else {
            deleteSelectedNotification(selected);
            AdminAlerts.delSuccess("Уведомление удалено!");
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/ShowNotifications.fxml"));
            MainClient.primaryStage.setScene(new Scene(root));
            MainClient.primaryStage.show();
        }
    }

    public void toMainAdmin(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/AdminMenu.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    @FXML
    public void searchSeances() {

        ObservableList<Notification> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllNotifications());
        } catch (IOException e) {
            e.printStackTrace();
        }

        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        notification.setCellValueFactory(new PropertyValueFactory<>("changed"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        FilteredList<Notification> filteredData = new FilteredList<>(nSeances, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(oneSeance -> {
                if (newValue == null) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (oneSeance.getLogin().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (oneSeance.getTime().toString().indexOf(lowerCaseFilter) != -1) return true;
                else if (oneSeance.getDate().toString().indexOf(lowerCaseFilter) != -1) return true;
                else if (oneSeance.getChanged().indexOf(lowerCaseFilter) != -1) return true;
                else return false;
            });
        });
        SortedList<Notification> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Notification.comparatorProperty());
        Notification.setItems(sortedData);
    }
}
