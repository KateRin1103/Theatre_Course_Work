package controller.admin.bookings;

import controller.InteractionWithProgInterface;
import controller.MainClient;
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
import theatre.Booking;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static client.Client.deleteSelectedBooking;
import static client.Client.getAllBookingsToDel;

public class AdminDeleteBookings extends InteractionWithProgInterface implements Initializable {

    public TableView<Booking> Bookings;
    public TableColumn<Booking, String> seanceTitle;
    public TableColumn<Booking, String> login;
    public TableColumn<Booking, LocalDate> seanceDate;
    public TableColumn<Booking, LocalTime> seanceTime;
    public TableColumn<Booking, Integer> row;
    public TableColumn<Booking, Integer> place;

    public TextField filterField = new TextField();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Booking> nSeancesDel = null;
        try {
            nSeancesDel = FXCollections.observableArrayList(getAllBookingsToDel());
        } catch (IOException e) {
            e.printStackTrace();
        }

        seanceTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        seanceDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        seanceTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        row.setCellValueFactory(new PropertyValueFactory<>("row"));
        place.setCellValueFactory(new PropertyValueFactory<>("place"));
        Bookings.setItems(nSeancesDel);

        searchSeances();
        Bookings.setEditable(true);
    }

    public void delBooking(ActionEvent actionEvent) throws IOException {
        Booking selectedBooking = Bookings.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            AdminAlerts.showAlertNoSelected("Выберете бронь!");
        } else {
            deleteSelectedBooking(selectedBooking);
            AdminAlerts.delSuccess("Бронь удалена!");
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/DeleteBooking.fxml"));
            MainClient.primaryStage.setScene(new Scene(root));
            MainClient.primaryStage.show();
        }
    }

    @FXML
    public void searchSeances() {

        ObservableList<Booking> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllBookingsToDel());
        } catch (IOException e) {
            e.printStackTrace();
        }

        seanceTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        seanceTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        row.setCellValueFactory(new PropertyValueFactory<>("row"));
        place.setCellValueFactory(new PropertyValueFactory<>("place"));

        FilteredList<Booking> filteredData = new FilteredList<>(nSeances, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(oneSeance -> {
                if (newValue == null) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (oneSeance.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (oneSeance.getLogin().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (oneSeance.getTime().toString().indexOf(lowerCaseFilter) != -1) return true;
                else if (oneSeance.getDate().toString().indexOf(lowerCaseFilter) != -1) return true;
                else return false;
            });
        });
        SortedList<Booking> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Bookings.comparatorProperty());
        Bookings.setItems(sortedData);
    }
}
