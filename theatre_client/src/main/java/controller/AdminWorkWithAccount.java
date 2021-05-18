package controller;

import account.User;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static client.Client.deleteSelectedAccount;
import static client.Client.getAllAccountUser;
import static controller.Sample.windowRedact;

public class AdminWorkWithAccount implements Initializable {
    public TableView<User> User;
    public TableColumn<User, String> userLogin;
    public TableColumn<User, String> userPassword;
    public TableColumn<User, String> userSurname;
    public TableColumn<User, String> userName;
    public TableColumn<User, String> userPhone;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<User> nAccs = null;
        try {
            nAccs = FXCollections.observableArrayList(getAllAccountUser());
        } catch (IOException e) {
            e.printStackTrace();
        }

        userLogin.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        userPassword.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        userSurname.setCellValueFactory(new PropertyValueFactory<User, String>("lastname"));
        userName.setCellValueFactory(new PropertyValueFactory<User, String>("firstname"));
        userPhone.setCellValueFactory(new PropertyValueFactory<User, String>("phone"));
        User.setItems(nAccs);

        User.setEditable(true);
    }

    public void editLogin(ActionEvent actionEvent) throws IOException{
        User selectedAcc = User.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            showAlertNoSelected();
        }
        else {
            RedAccount.user = selectedAcc;
            windowRedact = new Stage();
            windowRedact.setTitle("Редактирование логина");
            windowRedact.setResizable(false);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/Redact/RedactLogin.fxml"));
            Scene scene = new Scene(root);
            MainClient.primaryStage.show();
            windowRedact.setScene(scene);
            windowRedact.show();
        }
    }

    public void editPassword(ActionEvent actionEvent) throws IOException{
        User selectedAcc = User.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            showAlertNoSelected();
        }
        else {
            RedAccount.user = selectedAcc;
            windowRedact = new Stage();
            windowRedact.setTitle("Редактирование пароля");
            windowRedact.setResizable(false);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/Redact/RedactPassword.fxml"));
            Scene scene = new Scene(root);
            MainClient.primaryStage.show();
            windowRedact.setScene(scene);
            windowRedact.show();
        }
    }

    public void editSurname(ActionEvent actionEvent) throws IOException{
        User selectedAcc = User.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            showAlertNoSelected();
        }
        else {
            RedAccount.user = selectedAcc;
            windowRedact = new Stage();
            windowRedact.setTitle("Редактирование фамилии");
            windowRedact.setResizable(false);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/Redact/RedactSurname.fxml"));
            Scene scene = new Scene(root);
            MainClient.primaryStage.show();
            windowRedact.setScene(scene);
            windowRedact.show();
        }
    }

    public void editName(ActionEvent actionEvent) throws IOException{
        User selectedAcc = User.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            showAlertNoSelected();
        }
        else {
            RedAccount.user = selectedAcc;
            windowRedact = new Stage();
            windowRedact.setTitle("Редактирование имени");
            windowRedact.setResizable(false);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/Redact/RedactName.fxml"));
            Scene scene = new Scene(root);
            MainClient.primaryStage.show();
            windowRedact.setScene(scene);
            windowRedact.show();
        }
    }

    public void editMail(ActionEvent actionEvent) throws IOException{
        User selectedAcc = User.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            showAlertNoSelected();
        }
        else {
            RedAccount.user = selectedAcc;
            windowRedact = new Stage();
            windowRedact.setTitle("Редактирование почты");
            windowRedact.setResizable(false);
//            windowRedact.getIcons().add(new Image("/resources/icon.png"));
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/Redact/RedactMail.fxml"));
            Scene scene = new Scene(root);
            MainClient.primaryStage.show();
            windowRedact.setScene(scene);
            windowRedact.show();
        }
    }

    public void toMain (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/start/Main.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toMainAdmin (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/AdminMenu.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toAddNewUser (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/AddNewUser.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toShowUser (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/ShowUser.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toShowSeances (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/ShowSeances.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toDeleteUser(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/DeleteUser.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void delAccount(ActionEvent actionEvent) {
        User selectedAcc = User.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            showAlertNoSelected();
        }
        else {
            deleteSelectedAccount(selectedAcc.getAccount().getLogin());
            User.getItems().removeAll(selectedAcc);
            delSuccess();
        }
    }

    public void showAlertNoSelected() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Предупреждение");
        alert.setHeaderText(null);
        alert.setContentText("Выберите пользователя!");
        alert.showAndWait();
    }

    public void delSuccess(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Пользователь удалён!");
        alert.showAndWait();
    }

    public void toAddNewProduct(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/AddNewProduct.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toRedactUser(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/RedactUser.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toShowShowPurchasedProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/ShowPurchasedProduct.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void toProfitСalculation(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/adminActions/ProfitСalculation.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }


}
