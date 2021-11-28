package controller.admin.seances;

import controller.InteractionWithProgInterface;
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
import theatre.Seance;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static client.Client.deleteSelectedSeance;
import static client.Client.getAllSeances;
import static controller.Sample.windowRedact;

public class AdminWorkWithSeances extends InteractionWithProgInterface implements Initializable {

    public TableView<Seance> Seance;
    public TableColumn<Seance, String> seanceTitle;
    public TableColumn<Seance, LocalTime> seanceTime;
    public TableColumn<Seance, LocalDate> seanceDate;
    public TableColumn<Seance, Integer> seancePrice;

    public TextField filterField = new TextField();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Seance> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllSeances());
        } catch (IOException e) {
            e.printStackTrace();
        }

        seanceTitle.setCellValueFactory(new PropertyValueFactory<Seance, String>("film"));
        seanceTime.setCellValueFactory(new PropertyValueFactory<Seance, LocalTime>("time"));
        seanceDate.setCellValueFactory(new PropertyValueFactory<Seance, LocalDate>("date"));
        seancePrice.setCellValueFactory(new PropertyValueFactory<Seance,Integer>("price"));
        Seance.setItems(nSeances);

        Seance.setEditable(true);
        searchSeances();
    }

    @FXML
    public void searchSeances() {

        ObservableList<Seance> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllSeances());
        } catch (IOException e) {
            e.printStackTrace();
        }

        seanceTitle.setCellValueFactory(new PropertyValueFactory<Seance, String>("film"));
        seanceTime.setCellValueFactory(new PropertyValueFactory<Seance, LocalTime>("time"));
        seanceDate.setCellValueFactory(new PropertyValueFactory<Seance, LocalDate>("date"));
        seancePrice.setCellValueFactory(new PropertyValueFactory<Seance, Integer>("price"));

        FilteredList<Seance> filteredData = new FilteredList<>(nSeances, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(oneSeance -> {
                if (newValue == null) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (oneSeance.getFilm().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(oneSeance.getPrice()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (oneSeance.getTime().toString().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (oneSeance.getDate().toString().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;
            });
        });
        SortedList<Seance> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Seance.comparatorProperty());
        Seance.setItems(sortedData);
    }

    public void toAddSeance(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/AddSeance.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void delSeance(ActionEvent actionEvent) throws IOException {
        Seance selectedSeance = Seance.getSelectionModel().getSelectedItem();
        if (selectedSeance == null) {
            AdminAlerts.showAlertNoSelected("Сеанс не выбран!");
        } else {
            deleteSelectedSeance(selectedSeance);
            AdminAlerts.delSuccess("Сеанс спешно удалён!");
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/AdminMenu.fxml"));
            MainClient.primaryStage.setScene(new Scene(root));
            MainClient.primaryStage.show();
        }
    }

    public void editTime(ActionEvent actionEvent) throws IOException{
        Seance selectedAcc = Seance.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            AdminAlerts.showAlertNoSelected("Сеанс не выбран!");
        }
        else {
            EditSeance.seance = selectedAcc;
            windowRedact = new Stage();
            windowRedact.setTitle("Редактирование времени");
            windowRedact.setResizable(false);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/Redact/RedactTime.fxml"));
            Scene scene = new Scene(root);
            MainClient.primaryStage.show();
            windowRedact.setScene(scene);
            windowRedact.show();
        }
    }

    public void toMainAdmin(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/AdminMenu.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }
}
