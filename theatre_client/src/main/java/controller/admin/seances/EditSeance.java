package controller.admin.seances;

import controller.InteractionWithProgInterface;
import controller.MainClient;
import controller.Sample;
import controller.alerts.AdminAlerts;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
            toRedactFilm(new ActionEvent());
            Sample.windowRedact.close();
        }
    }

    public void updateTime(MouseEvent actionEvent) throws IOException {
        newTime.setItems(FXCollections.observableArrayList(getFreeTime(seance.getDate())));
    }

    public void toRedactFilm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/RedactSeances.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }
}
