package controller.admin.accounts;

import account.User;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static client.Client.deleteSelectedAccount;
import static client.Client.getAllAccountUser;
import static controller.Sample.windowRedact;

public class AdminWorkWithAccount extends InteractionWithProgInterface implements Initializable {
    public TableView<User> User;
    public TableColumn<User, String> userLogin;
    public TableColumn<User, String> userPassword;
    public TableColumn<User, String> userSurname;
    public TableColumn<User, String> userName;
    public TableColumn<User, String> userPhone;

    public PasswordField userPasswordField;
    public TextField userLoginField;
    public TextField userSurnameField;
    public TextField userNameField;
    public TextField userPhoneField;

    public String getLogin() {
        return userLoginField.getText();
    }

    public TextField filterField = new TextField();

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
        searchUser();
    }

    public void editLogin(ActionEvent actionEvent) throws IOException{
        User selectedAcc = User.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            AdminAlerts.showAlertNoSelected("Пользователь не выбран!");
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

    public void editSurname(ActionEvent actionEvent) throws IOException{
        User selectedAcc = User.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            AdminAlerts.showAlertNoSelected("Пользователь не выбран!");
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
            AdminAlerts.showAlertNoSelected("Пользователь не выбран!");
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
            AdminAlerts.showAlertNoSelected("Пользователь не выбран!");
        }
        else {
            RedAccount.user = selectedAcc;
            windowRedact = new Stage();
            windowRedact.setTitle("Редактирование почты");
            windowRedact.setResizable(false);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/Redact/RedactMail.fxml"));
            Scene scene = new Scene(root);
            MainClient.primaryStage.show();
            windowRedact.setScene(scene);
            windowRedact.show();
        }
    }

    public void editPassword(ActionEvent actionEvent) throws IOException{
        User selectedAcc = User.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            AdminAlerts.showAlertNoSelected("Пользователь не выбран!");
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

    public void toMainAdmin (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login/AdminMenu.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    public void delAccount(ActionEvent actionEvent) throws IOException {
        User selectedAcc = User.getSelectionModel().getSelectedItem();
        if (selectedAcc == null) {
            AdminAlerts.showAlertNoSelected("Пользователь не выбран!");
        }
        else {
            deleteSelectedAccount(selectedAcc.getAccount().getLogin());
            AdminAlerts.delSuccess("Пользователь удалён!");
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/RedactUser.fxml"));
            MainClient.primaryStage.setScene(new Scene(root));
            MainClient.primaryStage.show();
        }
    }

    public void toAddNewUser(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/AddNewUser.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }

    /*public void addNewUser(ActionEvent actionEvent) {
        if (userLoginField.getText().isEmpty() || userPasswordField.getText().isEmpty()
                || userNameField.getText().isEmpty() || userSurnameField.getText().isEmpty() || userPhone.getText().isEmpty())
            AdminAlerts.showAlertEmpty();
        else if (checkSameUser(getLogin())) {
            userLoginField.clear();
            AdminAlerts.showAlertSameLogin();
        } else if (!checkCorrectAccount(userNameField.getText(), userSurnameField.getText())) {
            userNameField.clear();
            userSurnameField.clear();
            AdminAlerts.showAlertIncorrect("Данные пользователя введены невено!");
        } else {
            addNewAccountUser(new User(userNameField.getText(), userSurnameField.getText(),
                    userPhoneField.getText(), new Account(userLoginField.getText(), userPasswordField.getText())));
            clearAllFields();
            AdminAlerts.showAlertSuccess("Пользователь успешно добален!");
        }
    }*/

    /*public void showAlertEmpty() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ОШИБКА");
        alert.setHeaderText("Справка");
        alert.setContentText("Заполните все поля!");
        alert.showAndWait();
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

    public void showAlertSameLogin() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ОШИБКА");
        alert.setHeaderText("Справка");
        alert.setContentText("Учётная запись с таким логином уже существует!");
        alert.showAndWait();
    }

    public void showAlertIncorrect() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ОШИБКА");
        alert.setHeaderText("Справка");
        alert.setContentText("Введите корректно имя и фамилию!");
        alert.showAndWait();
    }

    public void showAlertSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Поздравляем");
        alert.setHeaderText(null);
        alert.setContentText("Запись успешно добавлена!");
        alert.showAndWait();
    }*/

    public void clearAllFields() {
        userLoginField.clear();
        userPasswordField.clear();
        userSurnameField.clear();
        userNameField.clear();
        userPhoneField.clear();
    }

    @FXML
    public void searchUser() {

        ObservableList<User> nSeances = null;
        try {
            nSeances = FXCollections.observableArrayList(getAllAccountUser());
        } catch (IOException e) {
            e.printStackTrace();
        }

        userLogin.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        userPassword.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        userSurname.setCellValueFactory(new PropertyValueFactory<User, String>("lastname"));
        userName.setCellValueFactory(new PropertyValueFactory<User, String>("firstname"));
        userPhone.setCellValueFactory(new PropertyValueFactory<User, String>("phone"));


        FilteredList<User> filteredData = new FilteredList<>(nSeances, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> {
                if (newValue == null) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (user.getLogin().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (user.getFirstname().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (user.getLastname().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (user.getPhone().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return false;
            });
        });
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(User.comparatorProperty());
        User.setItems(sortedData);
    }
}
