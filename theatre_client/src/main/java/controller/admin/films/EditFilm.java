package controller.admin.films;

import controller.InteractionWithProgInterface;
import controller.Sample;
import controller.alerts.AdminAlerts;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import theatre.Film;

import java.io.IOException;

import static client.Client.*;
import static java.lang.Integer.parseInt;

public class EditFilm extends InteractionWithProgInterface {
    public static Film film;
    public TextField newTitle;
    public TextField newDuration;
    public TextField newRent;

    public void createNewRent(ActionEvent actionEvent) throws IOException {
        int enter = parseInt(newRent.getText());
        if (newRent.getText().isEmpty())
            AdminAlerts.showAlertEmpty();
        else {
            editRentalCost(enter, film.getTitle());
            AdminAlerts.editSuccess();
            toRedactFilm(new ActionEvent());
            Sample.windowRedact.close();
        }
    }

    public void createNewTitle(ActionEvent actionEvent) throws IOException {
        String enter = newTitle.getText();
        if (enter.isEmpty())
            AdminAlerts.showAlertEmpty();
        else {
            editTitle(enter, film.getTitle());
            AdminAlerts.editSuccess();
            toRedactFilm(new ActionEvent());
            Sample.windowRedact.close();
        }
    }

    public void createNewDuration(ActionEvent actionEvent) throws IOException {
        int enter = parseInt(newDuration.getText());
        if (newDuration.getText().isEmpty())
            AdminAlerts.showAlertEmpty();
        else {
            editDuration(enter, film.getTitle());
            AdminAlerts.editSuccess();
            toRedactFilm(new ActionEvent());
            Sample.windowRedact.close();
        }
    }
}
