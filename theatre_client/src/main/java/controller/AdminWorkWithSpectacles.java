package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import theatre.Spectacle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static client.Client.deleteSelectedSpectacle;
import static client.Client.getAllSpectacles;

public class AdminWorkWithSpectacles implements Initializable {
    public TableView<Spectacle> Spectacle;
    public TableColumn<Spectacle, String> title;
    public TableColumn<Spectacle, Integer> duration;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Spectacle> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllSpectacles());
        } catch (IOException e) {
            e.printStackTrace();
        }

        title.setCellValueFactory(new PropertyValueFactory<Spectacle, String>("title"));
        duration.setCellValueFactory(new PropertyValueFactory<Spectacle, Integer>("duration"));
        Spectacle.setItems(nSeances);

        Spectacle.setEditable(true);
    }

    public void delSpectacle(ActionEvent actionEvent) {
        Spectacle selectedAcc = Spectacle.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            showAlertNoSelected();
        }
        else {
            deleteSelectedSpectacle(selectedAcc.getTitle());
            Spectacle.getItems().removeAll(selectedAcc);
            delSuccess();
        }
    }

    public void delSuccess(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Спектакль удалён!");
        alert.showAndWait();
    }

    public void showAlertNoSelected() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Предупреждение");
        alert.setHeaderText(null);
        alert.setContentText("Выберите спектакль!");
        alert.showAndWait();
    }

    public void toMainAdmin(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/AdminMenu.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

}
