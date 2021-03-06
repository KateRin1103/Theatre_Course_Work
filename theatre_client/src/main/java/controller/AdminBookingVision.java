package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import theatre.Booking;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static client.Client.deleteSelectedBooking;
import static client.Client.getAllBookings;

public class AdminBookingVision implements Initializable {

    public TableView<Booking> Booking;
    public TableColumn<Booking, String> seanceTitle;
    public TableColumn<Booking, String> login;
    public TableColumn<Booking, LocalDate> seanceDate;
    public TableColumn<Booking, LocalTime> seanceTime;
    public TableColumn<Booking, Integer> row;
    public TableColumn<Booking, Integer> place;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Booking> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllBookings());
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

        Booking.setEditable(true);
    }

    public void showAlertNoSelected() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("????????????????????????????");
        alert.setHeaderText(null);
        alert.setContentText("???????????????? ??????????!");
        alert.showAndWait();
    }

    public void delSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("?????????? ????????????!");
        alert.showAndWait();
    }

    public void delBooking(ActionEvent actionEvent) {
        Booking selectedBooking = Booking.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            showAlertNoSelected();
        } else {
            Booking.getItems().removeAll(selectedBooking);
            deleteSelectedBooking(selectedBooking);
            delSuccess();
        }
    }

    public void toMainAdmin(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/AdminMenu.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }
}
