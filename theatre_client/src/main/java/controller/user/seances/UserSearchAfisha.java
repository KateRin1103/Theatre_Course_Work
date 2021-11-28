package controller.user.seances;

import controller.UserInteractionWithProgInterface;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import theatre.Seance;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static client.Client.getAllSeances;

public class UserSearchAfisha extends UserInteractionWithProgInterface implements Initializable {

    public TableView<theatre.Seance> Seance;
    public TableColumn<Seance, String> seanceTitle;
    public TableColumn<Seance, LocalTime> seanceTime;
    public TableColumn<Seance, LocalDate> seanceDate;
    public TableColumn<Seance, Integer> seancePrice;

    public DatePicker startPicker = new DatePicker();
    public DatePicker endPicker = new DatePicker();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
        searchSeancesDate();
    }

    public void update() {

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

    @FXML
    public void searchSeancesDate() {

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

        filteredData.predicateProperty().bind(Bindings.createObjectBinding(() -> {
                    LocalDate minDate = startPicker.getValue();
                    LocalDate maxDate = endPicker.getValue();

                    // get final values != null
                    final LocalDate finalMin = minDate == null ? LocalDate.MIN : minDate;
                    final LocalDate finalMax = maxDate == null ? LocalDate.MAX : maxDate;

                    // values for openDate need to be in the interval [finalMin, finalMax]
                    return ti -> !finalMin.isAfter(ti.getDate()) && !finalMax.isBefore(ti.getDate());
                },
                startPicker.valueProperty(),
                endPicker.valueProperty()));
        Seance.setItems(filteredData);
    }
}

