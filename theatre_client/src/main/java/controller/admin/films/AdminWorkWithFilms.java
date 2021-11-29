package controller.admin.films;

import controller.admin.InteractionWithProgInterface;
import controller.MainClient;
import controller.alerts.AdminAlerts;
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
import javafx.stage.Stage;
import theatre.Film;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static client.Client.deleteSelectedFilm;
import static client.Client.getAllFilms;
import static controller.Sample.windowRedact;

public class AdminWorkWithFilms extends InteractionWithProgInterface implements Initializable {
    public TableView<Film> Film;
    public TableColumn<Film, String> title;
    public TableColumn<Film, Integer> duration;
    public TableColumn<Film, Integer> rent;


    public TextField filterField = new TextField();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Film> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllFilms());
        } catch (IOException e) {
            e.printStackTrace();
        }

        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        rent.setCellValueFactory(new PropertyValueFactory<>("rent"));
        Film.setItems(nSeances);

        Film.setEditable(true);
        searchFilms();
    }

    @FXML
    public void searchFilms() {
        ObservableList<Film> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllFilms());
        } catch (IOException e) {
            e.printStackTrace();
        }

        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        rent.setCellValueFactory(new PropertyValueFactory<>("rent"));

        FilteredList<Film> filteredData = new FilteredList<>(nSeances, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(oneSeance -> {
                if (newValue == null) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (oneSeance.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(oneSeance.getDuration()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (String.valueOf(oneSeance.getRent()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else return false;
            });
        });
        SortedList<Film> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Film.comparatorProperty());
        Film.setItems(sortedData);
    }

    public void delFilm(ActionEvent actionEvent) throws IOException {
        Film selectedAcc = Film.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            AdminAlerts.showAlertNoSelected("Фильм не выбран!");
        }
        else {
            deleteSelectedFilm(selectedAcc.getTitle());
            AdminAlerts.delSuccess("Фильм успешно удалён!");
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/RedactFilm.fxml"));
            MainClient.primaryStage.setScene(new Scene(root));
            MainClient.primaryStage.show();
        }
    }

    public void editTitle(ActionEvent actionEvent) throws IOException{
        Film selectedAcc = Film.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            AdminAlerts.showAlertNoSelected("Фильм не выбран!");
        }
        else {
            EditFilm.film = selectedAcc;
            windowRedact = new Stage();
            windowRedact.setTitle("Редактирование названия");
            windowRedact.setResizable(false);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/Redact/RedactTitle.fxml"));
            Scene scene = new Scene(root);
            MainClient.primaryStage.show();
            windowRedact.setScene(scene);
            windowRedact.show();
        }
    }

    public void editDuration(ActionEvent actionEvent) throws IOException{
        Film selectedAcc = Film.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            AdminAlerts.showAlertNoSelected("Фильм не выбран!");
        }
        else {
            EditFilm.film = selectedAcc;
            windowRedact = new Stage();
            windowRedact.setTitle("Редактирование продолжительности");
            windowRedact.setResizable(false);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/Redact/RedactDuration.fxml"));
            Scene scene = new Scene(root);
            MainClient.primaryStage.show();
            windowRedact.setScene(scene);
            windowRedact.show();
        }
    }

    public void editRent(ActionEvent actionEvent) throws IOException{
        Film selectedAcc = Film.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            AdminAlerts.showAlertNoSelected("Фильм не выбран!");
        }
        else {
            EditFilm.film = selectedAcc;
            windowRedact = new Stage();
            windowRedact.setTitle("Редактирование затрат на показ/мес.");
            windowRedact.setResizable(false);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/Redact/RedactRentalCost.fxml"));
            Scene scene = new Scene(root);
            MainClient.primaryStage.show();
            windowRedact.setScene(scene);
            windowRedact.show();
        }
    }
}
