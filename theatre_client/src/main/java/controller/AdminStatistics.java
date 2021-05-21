package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import statistics.Statistics;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static client.Client.getStatistics;

public class AdminStatistics implements Initializable {

    public TableView<Statistics> Statistics;
    public TableColumn<Statistics, String> titleS;
    public TableColumn<Statistics, LocalDate> date;
    public TableColumn<Statistics, Integer> goal;
    public TableColumn<Statistics, Integer> result;
    public TableColumn<Statistics, Integer> numOfShows;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Statistics> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getStatistics());
        } catch (IOException e) {
            e.printStackTrace();
        }

        titleS.setCellValueFactory(new PropertyValueFactory<Statistics, String>("title"));
        date.setCellValueFactory(new PropertyValueFactory<Statistics, LocalDate>("date"));
        numOfShows.setCellValueFactory(new PropertyValueFactory<Statistics, Integer>("number_of_shows"));
        goal.setCellValueFactory(new PropertyValueFactory<Statistics, Integer>("goal"));
        result.setCellValueFactory(new PropertyValueFactory<Statistics, Integer>("result"));
        Statistics.setItems(nSeances);

        Statistics.setEditable(true);
    }

    public void toMainAdmin(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/AdminMenu.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

}
