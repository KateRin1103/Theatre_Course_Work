package controller;

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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import theatre.Booking;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static client.Client.deleteSelectedBooking;
import static client.Client.getAllBookingsByLogin;
import static controller.AccountLogin.enteredUserLogin;

public class UserDeleteBookings implements Initializable {

    public TableView<theatre.Booking> Booking;
    public TableColumn<Booking, String> seanceTitle;
    public TableColumn<Booking, String> login;
    public TableColumn<Booking, LocalDate> seanceDate;
    public TableColumn<Booking, LocalTime> seanceTime;
    public TableColumn<Booking, Integer> row;
    public TableColumn<Booking, Integer> place;

    @FXML
    private TextField filterField = new TextField();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Booking> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllBookingsByLogin(enteredUserLogin));
        } catch (IOException e) {
            e.printStackTrace();
        }

        seanceTitle.setCellValueFactory(new PropertyValueFactory<Booking, String>("title"));
        login.setCellValueFactory(new PropertyValueFactory<Booking, String>("login"));
        seanceDate.setCellValueFactory(new PropertyValueFactory<Booking, LocalDate>("date"));
        seanceTime.setCellValueFactory(new PropertyValueFactory<Booking, LocalTime>("time"));
        row.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("row"));
        place.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("place"));
        Booking.setItems(nSeances);

        searchBookings();

        Booking.setEditable(true);
    }

    public void toMainUser(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/UserMenu.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void showAlertNoSelected() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Предупреждение");
        alert.setHeaderText(null);
        alert.setContentText("Выберите сеанс!");
        alert.showAndWait();
    }

    public void delSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Сеанс удалён!");
        alert.showAndWait();
    }

    public void delBooking(ActionEvent actionEvent) throws IOException {
        Booking selectedBooking = Booking.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            showAlertNoSelected();
        } else {
            deleteSelectedBooking(selectedBooking);
            delSuccess();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/UserMenu.fxml"));
            MainClient.primaryStage.setScene(new Scene(root));
            MainClient.primaryStage.show();
        }
    }

    @FXML
    public void searchBookings() {
        ObservableList<Booking> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllBookingsByLogin(enteredUserLogin));
        } catch (IOException e) {
            e.printStackTrace();
        }

        seanceTitle.setCellValueFactory(new PropertyValueFactory<Booking, String>("title"));
        login.setCellValueFactory(new PropertyValueFactory<Booking, String>("login"));
        seanceDate.setCellValueFactory(new PropertyValueFactory<Booking, LocalDate>("date"));
        seanceTime.setCellValueFactory(new PropertyValueFactory<Booking, LocalTime>("time"));
        row.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("row"));
        place.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("place"));

        FilteredList<Booking> filteredData = new FilteredList<>(nSeances, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(oneBooking -> {
                if (newValue == null) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (oneBooking.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }  else if (oneBooking.getTime().toString().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (oneBooking.getDate().toString().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });
        SortedList<Booking> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Booking.comparatorProperty());
        Booking.setItems(sortedData);
    }

}
