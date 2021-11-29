package controller.user;

import controller.AutoCompleteComboBoxListener;
import controller.alerts.AdminAlerts;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.Rating;

import java.io.IOException;

import static client.Client.*;
import static controller.AccountLogin.enteredUserLogin;

public class AddRating extends UserInteractionWithProgInterface {
    public ComboBox<String> film;
    public TextArea comment;
    public Rating rating;

    public void updateList(MouseEvent actionEvent) throws IOException {
        film.setEditable(true);
        AutoCompleteComboBoxListener.setItems(film, getFilmTitles());
    }

    public void addNewRating(ActionEvent actionEvent) {
        if (film.getValue() == null || rating.getRating() == 0) {
            AdminAlerts.showAlertEmptySpect();
        } else {
            try {
                userAddNewRating(new statistics.Rating(
                        enteredUserLogin,
                        film.getValue(),
                        (float) rating.getRating(),
                        comment.getText()));
                AdminAlerts.showAlertSuccess("Отзыв успешно добавлен!");
            } catch (NumberFormatException ex) {
                AdminAlerts.showAlertEmptySpect();
            }
            film.setValue(null);
            rating.setRating(0);
            comment.setText(null);
        }
    }
}
