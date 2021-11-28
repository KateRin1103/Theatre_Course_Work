package controller.admin.films;

import controller.InteractionWithProgInterface;
import controller.MainClient;
import controller.alerts.AdminAlerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import theatre.Film;

import java.io.IOException;

import static client.Client.addNewFilm;
import static java.lang.Integer.parseInt;

public class AddNewFilm extends InteractionWithProgInterface {
    public TextField titleReg;
    public TextField durationReg;
    public TextField rentalPrice;

    public void addNewFilms(ActionEvent actionEvent) {
        if (titleReg.getText().isEmpty() || durationReg.getText().isEmpty())
            AdminAlerts.showAlertEmpty();
        else if (checkCorrectInt(durationReg.getText()) && checkCorrectInt(rentalPrice.getText())) {
            durationReg.clear();
            rentalPrice.clear();
            AdminAlerts.showAlertEmptySpect();
        } else {
            try {
                int dur = parseInt(durationReg.getText());
                addNewFilm(new Film(titleReg.getText(), dur, parseInt(rentalPrice.getText())));
               AdminAlerts.showAlertSuccess("Фильм добавлен!");
            } catch (NumberFormatException ex) {
                AdminAlerts.showAlertEmptySpect();
            }
            titleReg.clear();
            durationReg.clear();
            rentalPrice.clear();
        }
    }

    public void toEditFilm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/RedactFilm.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public static boolean checkCorrectInt(String str) {
        for (var i = 0; i < str.length(); i++) {
            if (!(str.charAt(0) >= 'А' && str.charAt(0) <= 'Я'))
                return false;
            if (!(str.charAt(i) >= 'а' && str.charAt(i) <= 'я')
                    && !(str.charAt(i) >= 'А' && str.charAt(i) <= 'Я')
                    && !(str.charAt(i) == ' ') && !(str.charAt(i) == '-'))
                return false;
            if (!(str.charAt(0) >= 'A' && str.charAt(0) <= 'Z'))
                return false;
            if (!(str.charAt(i) >= 'a' && str.charAt(i) <= 'z')
                    && !(str.charAt(i) >= 'А' && str.charAt(i) <= 'Я'))
                return false;
        }
        return true;
    }
}
