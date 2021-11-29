package controller.admin.seances;

import controller.AutoCompleteComboBoxListener;
import controller.admin.InteractionWithProgInterface;
import controller.alerts.AdminAlerts;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import theatre.Seance;

import java.io.IOException;
import java.time.LocalTime;

import static client.Client.*;
import static java.lang.Integer.parseInt;

public class AddNewSeance extends InteractionWithProgInterface {

    public ComboBox<String> titleChoose;
    public Label label;
    public ComboBox<LocalTime> timeChoose;
    public ComboBox price;
    public DatePicker seanceDateChoose;

    public void updateList(MouseEvent actionEvent) throws IOException {
        titleChoose.setEditable(true);
        AutoCompleteComboBoxListener.setItems(titleChoose, getFilmTitles());
    }

    public void updateTime(MouseEvent actionEvent) throws IOException {
        if (seanceDateChoose.getValue() != null) {
            timeChoose.setItems(FXCollections.observableArrayList(getFreeTime(seanceDateChoose.getValue())));
        } else {
            AdminAlerts.showAlertEmpty();
        }
    }

    public void addNewSeance(ActionEvent actionEvent) {
        if (titleChoose.getValue() == null || seanceDateChoose.getValue() == null || timeChoose.getValue() == null
                || price.getValue() == null) {
           AdminAlerts.showAlertEmptySpect();
        } else {
            try {
                addNewSeances(new Seance(
                        titleChoose.getValue(),
                        seanceDateChoose.getValue(),
                        timeChoose.getValue(),
                        parseInt(String.valueOf(price.getValue()))));
                AdminAlerts.showAlertSuccess("Сеанс успешно добавлен!");
            } catch (NumberFormatException ex) {
                AdminAlerts.showAlertEmptySpect();
            }
            titleChoose.setValue(null);
            seanceDateChoose.setValue(null);
            timeChoose.setValue(null);
            price.setValue(null);
        }
    }
}

