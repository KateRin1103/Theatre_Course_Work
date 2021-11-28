package controller.user.seances;

import controller.UserInteractionWithProgInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.util.stream.Stream;

import static client.Client.*;
import static controller.AccountLogin.enteredUserLogin;
import static java.lang.Integer.parseInt;

public class UserSeancesVision extends UserInteractionWithProgInterface implements Initializable {

    public TableView<theatre.Seance> Seance;
    public TableColumn<Seance, String> seanceTitle;
    public TableColumn<Seance, LocalTime> seanceTime;
    public TableColumn<Seance, LocalDate> seanceDate;
    public TableColumn<Seance, Integer> seancePrice;

    ObservableList<Seance> dataList;
    @FXML
    private TextField filterField = new TextField();
    @FXML
    ToggleGroup seats = new ToggleGroup();
    @FXML
    public RadioButton s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, s21, s22, s23, s24, s25;

    int selectedPlace, selectedRow = 0;
    int index = -1;

    @Override
    synchronized public void initialize(URL location, ResourceBundle resources) {
        update();
        searchSeances();
    }

    synchronized public void update() {
        Stream.of(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, s21, s22, s23, s24, s25)
                .forEach(s -> s.setVisible(false));
        ObservableList<Seance> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllSeances());
        } catch (IOException e) {
            e.printStackTrace();
        }
        seanceTitle.setCellValueFactory(new PropertyValueFactory<Seance, String>("film"));
        seanceTime.setCellValueFactory(new PropertyValueFactory<Seance, LocalTime>("time"));
        seanceDate.setCellValueFactory(new PropertyValueFactory<Seance, LocalDate>("date"));
        seancePrice.setCellValueFactory(new PropertyValueFactory<Seance, Integer>("price"));

        Seance.setItems(nSeances);

        Seance.setEditable(true);
    }

    synchronized public void getSeat() {
        if (s1.isSelected()) {
            selectedPlace = 1;
            selectedRow = 1;
        }
        if (s2.isSelected()) {
            selectedPlace = 2;
            selectedRow = 1;
        }
        if (s3.isSelected()) {
            selectedPlace = 3;
            selectedRow = 1;
        }
        if (s4.isSelected()) {
            selectedPlace = 4;
            selectedRow = 1;
        }
        if (s5.isSelected()) {
            selectedPlace = 5;
            selectedRow = 1;
        }
        if (s6.isSelected()) {
            selectedPlace = 1;
            selectedRow = 2;
        }
        if (s7.isSelected()) {
            selectedPlace = 2;
            selectedRow = 2;
        }
        if (s8.isSelected()) {
            selectedPlace = 3;
            selectedRow = 2;
        }
        if (s9.isSelected()) {
            selectedPlace = 4;
            selectedRow = 2;
        }
        if (s10.isSelected()) {
            selectedPlace = 5;
            selectedRow = 2;
        }
        if (s11.isSelected()) {
            selectedPlace = 1;
            selectedRow = 3;
        }
        if (s12.isSelected()) {
            selectedPlace = 2;
            selectedRow = 3;
        }
        if (s13.isSelected()) {
            selectedPlace = 3;
            selectedRow = 3;
        }
        if (s14.isSelected()) {
            selectedPlace = 4;
            selectedRow = 3;
        }
        if (s15.isSelected()) {
            selectedPlace = 5;
            selectedRow = 3;
        }
        if (s16.isSelected()) {
            selectedPlace = 1;
            selectedRow = 4;
        }
        if (s17.isSelected()) {
            selectedPlace = 2;
            selectedRow = 4;
        }
        if (s18.isSelected()) {
            selectedPlace = 3;
            selectedRow = 4;
        }
        if (s19.isSelected()) {
            selectedPlace = 4;
            selectedRow = 4;
        }
        if (s20.isSelected()) {
            selectedPlace = 5;
            selectedRow = 4;
        }
        if (s21.isSelected()) {
            selectedPlace = 1;
            selectedRow = 5;
        }
        if (s22.isSelected()) {
            selectedPlace = 2;
            selectedRow = 5;
        }
        if (s23.isSelected()) {
            selectedPlace = 3;
            selectedRow = 5;
        }
        if (s24.isSelected()) {
            selectedPlace = 4;
            selectedRow = 5;
        }
        if (s25.isSelected()) {
            selectedPlace = 5;
            selectedRow = 5;
        }
    }

    synchronized public void addBookingUser(ActionEvent actionEvent) {
        String selectedTitle = Seance.getSelectionModel().getSelectedItem().getFilm();
        LocalDate selectedDate = Seance.getSelectionModel().getSelectedItem().getDate();
        LocalTime selectedTime = Seance.getSelectionModel().getSelectedItem().getTime();
        String login = enteredUserLogin;
        getSeat();
        searchSeances();
        Booking booking = new Booking(selectedRow, selectedPlace, login, selectedTitle, selectedDate, selectedTime, 0);
        if (Seance.getSelectionModel().getSelectedItem() == null || selectedRow == 0 || selectedPlace == 0) {
            showAlertNoSelected();
        } else {
            try {
                addBooking(booking);
                showAlertSuccess();
            } catch (NumberFormatException ex) {
                showAlertEmptySpect();
            }
        }
        Stream.of(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, s21, s22, s23, s24, s25).
                forEach(s -> s.setSelected(false));
        Stream.of(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, s21, s22, s23, s24, s25).
                forEach(s -> s.setVisible(false));
    }


    @FXML
    synchronized public void searchSeances() {
        Stream.of(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, s21, s22, s23, s24, s25).
                forEach(s -> s.setSelected(false));
        Stream.of(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, s21, s22, s23, s24, s25).
                forEach(s -> s.setVisible(false));
        ObservableList<Seance> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllSeances());
        } catch (IOException e) {
            e.printStackTrace();
        }

        seanceTitle.setCellValueFactory(new PropertyValueFactory<Seance, String>("film"));
        seanceTime.setCellValueFactory(new PropertyValueFactory<Seance, LocalTime>("time"));
        seanceDate.setCellValueFactory(new PropertyValueFactory<Seance, LocalDate>("date"));
        seancePrice.setCellValueFactory(new PropertyValueFactory<Seance, Integer>("price"));

        FilteredList<Seance> filteredData = new FilteredList<>(nSeances, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(oneSeance -> {
                if (newValue == null) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (oneSeance.getFilm().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(oneSeance.getPrice()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (oneSeance.getTime().toString().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (oneSeance.getDate().toString().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });
        SortedList<Seance> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Seance.comparatorProperty());
        Seance.setItems(sortedData);
    }

    synchronized public void getSelected(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Stream.of(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, s21, s22, s23, s24, s25).
                forEach(s -> s.setVisible(true));
        Stream.of(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, s21, s22, s23, s24, s25).
                forEach(s -> s.setSelected(false));
        ArrayList<Integer> bookedPlaces = getSeancePlaces(Seance.getSelectionModel().getSelectedItem());
        for (Integer bookedPlace : bookedPlaces) {
            if (bookedPlace == parseInt(s2.getText())) s2.setVisible(false);
            if (bookedPlace == parseInt(s1.getText())) s1.setVisible(false);
            if (bookedPlace == parseInt(s3.getText())) s3.setVisible(false);
            if (bookedPlace == parseInt(s4.getText())) s4.setVisible(false);
            if (bookedPlace == parseInt(s5.getText())) s5.setVisible(false);
            if (bookedPlace == parseInt(s6.getText())) s6.setVisible(false);
            if (bookedPlace == parseInt(s7.getText())) s7.setVisible(false);
            if (bookedPlace == parseInt(s8.getText())) s8.setVisible(false);
            if (bookedPlace == parseInt(s9.getText())) s9.setVisible(false);
            if (bookedPlace == parseInt(s10.getText())) s10.setVisible(false);
            if (bookedPlace == parseInt(s11.getText())) s11.setVisible(false);
            if (bookedPlace == parseInt(s12.getText())) s12.setVisible(false);
            if (bookedPlace == parseInt(s13.getText())) s13.setVisible(false);
            if (bookedPlace == parseInt(s14.getText())) s14.setVisible(false);
            if (bookedPlace == parseInt(s15.getText())) s15.setVisible(false);
            if (bookedPlace == parseInt(s16.getText())) s16.setVisible(false);
            if (bookedPlace == parseInt(s17.getText())) s17.setVisible(false);
            if (bookedPlace == parseInt(s18.getText())) s18.setVisible(false);
            if (bookedPlace == parseInt(s19.getText())) s19.setVisible(false);
            if (bookedPlace == parseInt(s20.getText())) s20.setVisible(false);
            if (bookedPlace == parseInt(s21.getText())) s21.setVisible(false);
            if (bookedPlace == parseInt(s22.getText())) s22.setVisible(false);
            if (bookedPlace == parseInt(s23.getText())) s23.setVisible(false);
            if (bookedPlace == parseInt(s24.getText())) s24.setVisible(false);
            if (bookedPlace == parseInt(s25.getText())) s25.setVisible(false);
        }
    }
}
