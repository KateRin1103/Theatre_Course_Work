package controller.admin.statistics;

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
import statistics.Rating;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static client.Client.deleteSelectedRating;
import static client.Client.getAllRatings;

public class EditRatings extends InteractionWithProgInterface implements Initializable {
    public TableView<statistics.Rating> Rating;
    public TableColumn<Rating, String> login;
    public TableColumn<Rating, String> film;
    public TableColumn<Rating, Integer> rating;
    public TableColumn<Rating, String> comment;

    public TextField filterField = new TextField();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Rating> nSeancesDel = null;
        try {
            nSeancesDel = FXCollections.observableArrayList(getAllRatings());
        } catch (IOException e) {
            e.printStackTrace();
        }

        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        film.setCellValueFactory(new PropertyValueFactory<>("film"));
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        Rating.setItems(nSeancesDel);

        searchRatings();
        Rating.setEditable(true);
    }

    public void delRating(ActionEvent actionEvent) throws IOException {
        Rating selected = Rating.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AdminAlerts.showAlertNoSelected("Выберете отзыв!");
        } else {
            deleteSelectedRating(selected);
            AdminAlerts.delSuccess("Отзыв удалён!");
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/ShowRatings.fxml"));
            MainClient.primaryStage.setScene(new Scene(root));
            MainClient.primaryStage.show();
        }
    }

    @FXML
    public void searchRatings() {

        ObservableList<Rating> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllRatings());
        } catch (IOException e) {
            e.printStackTrace();
        }

        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        film.setCellValueFactory(new PropertyValueFactory<>("film"));
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        comment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        FilteredList<Rating> filteredData = new FilteredList<>(nSeances, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(oneSeance -> {
                if (newValue == null) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (oneSeance.getLogin().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (oneSeance.getFilm().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (String.valueOf(oneSeance.getRating()).indexOf(lowerCaseFilter) != -1) return true;
                else if (oneSeance.getComment().indexOf(lowerCaseFilter) != -1) return true;
                else return false;
            });
        });
        SortedList<Rating> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Rating.comparatorProperty());
        Rating.setItems(sortedData);
    }
}
