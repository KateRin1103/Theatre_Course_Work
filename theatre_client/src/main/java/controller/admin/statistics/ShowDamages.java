package controller.admin.statistics;

import controller.admin.InteractionWithProgInterface;
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
import statistics.Damages;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static client.Client.getDamages;

public class ShowDamages extends InteractionWithProgInterface implements Initializable {
    public TableView<statistics.Damages> Damages;
    public TableColumn<ShowDamages, String> title;
    public TableColumn<ShowDamages, Integer> sold;
    public TableColumn<ShowDamages, Integer> income;

    public TextField filterField = new TextField();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<statistics.Damages> nSeancesDel = null;
        try {
            nSeancesDel = FXCollections.observableArrayList(getDamages());
        } catch (IOException e) {
            e.printStackTrace();
        }

        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        sold.setCellValueFactory(new PropertyValueFactory<>("sold"));
        income.setCellValueFactory(new PropertyValueFactory<>("income"));
        Damages.setItems(nSeancesDel);

        searchDamages();
        Damages.setEditable(true);
    }

    @FXML
    public void searchDamages() {

        ObservableList<Damages> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getDamages());
        } catch (IOException e) {
            e.printStackTrace();
        }

        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        income.setCellValueFactory(new PropertyValueFactory<>("income"));
        FilteredList<Damages> filteredData = new FilteredList<>(nSeances, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(oneSeance -> {
                if (newValue == null) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (oneSeance.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (String.valueOf(oneSeance.getSold()).indexOf(lowerCaseFilter) != -1) return true;
                else if (String.valueOf(oneSeance.getIncome()).indexOf(lowerCaseFilter) != -1) return true;
                else return false;
            });
        });
        SortedList<Damages> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Damages.comparatorProperty());
        Damages.setItems(sortedData);
    }
}
