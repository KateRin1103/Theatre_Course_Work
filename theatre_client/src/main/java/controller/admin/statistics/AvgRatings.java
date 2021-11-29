package controller.admin.statistics;

import controller.InteractionWithProgInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import statistics.Rating;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static client.Client.getAvgRatings;

public class AvgRatings extends InteractionWithProgInterface implements Initializable {
    public TableView<statistics.Rating> Rating;
    public TableColumn<Rating, String> film;
    public TableColumn<Rating, Integer> rating;

    public TextField filterField = new TextField();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Rating> nSeancesDel = null;
        try {
            nSeancesDel = FXCollections.observableArrayList(getAvgRatings());
        } catch (IOException e) {
            e.printStackTrace();
        }

        film.setCellValueFactory(new PropertyValueFactory<>("film"));
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        Rating.setItems(nSeancesDel);

        searchRatings();
        Rating.setEditable(true);
    }

    @FXML
    public void searchRatings() {

        ObservableList<Rating> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAvgRatings());
        } catch (IOException e) {
            e.printStackTrace();
        }

        film.setCellValueFactory(new PropertyValueFactory<>("film"));
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        FilteredList<Rating> filteredData = new FilteredList<>(nSeances, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(oneSeance -> {
                if (newValue == null) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (oneSeance.getFilm().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (String.valueOf(oneSeance.getRating()).indexOf(lowerCaseFilter) != -1) return true;
                else return false;
            });
        });
        SortedList<Rating> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Rating.comparatorProperty());
        Rating.setItems(sortedData);
    }
}
