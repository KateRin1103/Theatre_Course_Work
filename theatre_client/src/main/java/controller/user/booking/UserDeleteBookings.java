package controller.user.booking;

import controller.MainClient;
import controller.user.UserInteractionWithProgInterface;
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

import static client.Client.*;
import static controller.AccountLogin.enteredUserLogin;

public class UserDeleteBookings extends UserInteractionWithProgInterface implements Initializable {

    public TableView<theatre.Booking> Booking;
    public TableColumn<Booking, String> seanceTitle;
    public TableColumn<Booking, String> login;
    public TableColumn<Booking, LocalDate> seanceDate;
    public TableColumn<Booking, LocalTime> seanceTime;
    public TableColumn<Booking, Integer> row;
    public TableColumn<Booking, Integer> place;
    public TableColumn<Booking, Integer> returning;

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
        returning.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("returning"));
        Booking.setItems(nSeances);

        searchBookings();

        Booking.setEditable(true);
    }

    public void delBooking(ActionEvent actionEvent) throws IOException {
        Booking selectedBooking = Booking.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            showAlertNoSelected();
        } else {
            setRequestedDelete(selectedBooking);
            delSuccess("Запрос на возврат отправлен!");
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/userActions/DeleteBooking.fxml"));
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
        returning.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("returning"));

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
