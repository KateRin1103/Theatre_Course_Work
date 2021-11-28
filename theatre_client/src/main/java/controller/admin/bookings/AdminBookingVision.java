package controller.admin.bookings;

import controller.InteractionWithProgInterface;
import controller.MainClient;
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

import static client.Client.getAllBookings;

public class AdminBookingVision extends InteractionWithProgInterface implements Initializable {

    public TableView<Booking> Booking;
    public TableColumn<Booking, String> seanceTitle;
    public TableColumn<Booking, String> login;
    public TableColumn<Booking, LocalDate> seanceDate;
    public TableColumn<Booking, LocalTime> seanceTime;
    public TableColumn<Booking, Integer> row;
    public TableColumn<Booking, Integer> place;

    public TextField filterField = new TextField();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Booking> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllBookings());
        } catch (IOException e) {
            e.printStackTrace();
        }

        seanceTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        seanceDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        seanceTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        row.setCellValueFactory(new PropertyValueFactory<>("row"));
        place.setCellValueFactory(new PropertyValueFactory<>("place"));
        Booking.setItems(nSeances);

        searchSeances();
        Booking.setEditable(true);
    }

    public void toMainAdmin(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/AdminMenu.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    @FXML
    public void searchSeances() {

        ObservableList<Booking> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllBookings());
        } catch (IOException e) {
            e.printStackTrace();
        }

        seanceTitle.setCellValueFactory(new PropertyValueFactory<Booking, String>("title"));
        login.setCellValueFactory(new PropertyValueFactory<Booking, String>("login"));
        seanceTime.setCellValueFactory(new PropertyValueFactory<Booking, LocalTime>("time"));
        row.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("row"));
        place.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("place"));

        FilteredList<Booking> filteredData = new FilteredList<>(nSeances, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(oneSeance -> {
                if (newValue == null) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (oneSeance.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (oneSeance.getLogin().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (oneSeance.getTime().toString().indexOf(lowerCaseFilter) != -1) return true;
                else if (oneSeance.getDate().toString().indexOf(lowerCaseFilter) != -1) return true;
                else return false;
            });
        });
        SortedList<Booking> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Booking.comparatorProperty());
        Booking.setItems(sortedData);
    }
}
