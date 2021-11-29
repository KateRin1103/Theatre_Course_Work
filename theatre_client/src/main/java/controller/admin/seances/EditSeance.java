package controller.admin.seances;

import controller.InteractionWithProgInterface;
import controller.Sample;
import controller.alerts.AdminAlerts;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import theatre.Seance;

import java.io.IOException;
import java.time.LocalTime;

import static client.Client.editTime;
import static client.Client.getFreeTime;

public class EditSeance extends InteractionWithProgInterface {
    public static Seance seance;
    public ComboBox<LocalTime> newTime;

    public void createNewTime(ActionEvent actionEvent) throws IOException {
        if (newTime.getValue() == null) {
           AdminAlerts.showAlertEmpty();
        } else {
            editTime(newTime.getValue(), seance.getTime());
            AdminAlerts.editSuccess();
            toRedactSeance(new ActionEvent());
            Sample.windowRedact.close();
        }
    }

    public void updateTime(MouseEvent actionEvent) throws IOException {
        newTime.setItems(FXCollections.observableArrayList(getFreeTime(seance.getDate())));
    }

}
