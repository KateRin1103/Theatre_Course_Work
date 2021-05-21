package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import theatre.Booking;
import theatre.Seance;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static client.Client.getAllSeances;
import static client.Client.getSeancePlaces;
import static controller.AccountLogin.enteredUserLogin;
import static java.lang.Integer.parseInt;

public class UserSeancesVision implements Initializable {

    public TableView<theatre.Seance> Seance;
    public TableColumn<Seance, String> seanceTitle;
    public TableColumn<Seance, LocalTime> seanceTime;
    public TableColumn<Seance, LocalDate> seanceDate;
    public TableColumn<Seance, Integer> seancePrice;
    public ComboBox row;
    public ComboBox place;
    public ArrayList<CheckBox> seats = new ArrayList<>(25);


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Seance> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllSeances());
        } catch (IOException e) {
            e.printStackTrace();
        }


        seanceTitle.setCellValueFactory(new PropertyValueFactory<Seance, String>("spectacle"));
        seanceTime.setCellValueFactory(new PropertyValueFactory<Seance, LocalTime>("time"));
        seanceDate.setCellValueFactory(new PropertyValueFactory<Seance, LocalDate>("date"));
        seancePrice.setCellValueFactory(new PropertyValueFactory<Seance, Integer>("price"));
        Seance.setItems(nSeances);

        Seance.setEditable(true);
    }

    public void toMainUser(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/UserMenu.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void delSeance(ActionEvent actionEvent) {
        String selectedTitle = Seance.getSelectionModel().getSelectedItem().getSpectacle();
        LocalDate selectedDate = Seance.getSelectionModel().getSelectedItem().getDate();
        LocalTime selectedTime = Seance.getSelectionModel().getSelectedItem().getTime();
        int selectedRow = parseInt(row.getValue().toString());
        int selectedPlace = parseInt(place.getValue().toString());
        String login = enteredUserLogin;
        Booking booking = new Booking(selectedRow, selectedPlace, login, selectedTitle, selectedDate, selectedTime);
        if (Seance.getSelectionModel().getSelectedItem() == null) {
            showAlertNoSelected();
        } else {
            return;
            // Seance.getItems().removeAll(selectedSeance);
            // deleteSelectedSeance(selectedSeance);
        }
    }

    public void getPlaces() throws IOException {
        if (Seance.getSelectionModel().getSelectedItem() == null) {
            showAlertNoSelected();
        } else { //вернуло лист занятых мест и
            //для каждой чекбокс по номеру установило галочки по id из листа занятых
            ArrayList<Integer> bookedPlaces = getSeancePlaces(Seance.getSelectionModel().getSelectedItem());
            while (bookedPlaces != null) {
                seats.get(bookedPlaces.remove(0)).setSelected(true);
            }
        }
    }

    public void showAlertNoSelected() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Предупреждение");
        alert.setHeaderText(null);
        alert.setContentText("Выберите сеанс!");
        alert.showAndWait();
    }

}
