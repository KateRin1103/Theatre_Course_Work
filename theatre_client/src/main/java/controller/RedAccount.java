package controller;

import account.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import static client.Client.*;

public class RedAccount extends InteractionWithProgInterface {
    public static User user;
    public PasswordField newPassword;
    public TextField newSurname;
    public TextField newName;
    public TextField newLogin;
    public TextField newMail;

    public void createNewPassword(ActionEvent actionEvent) throws IOException {
        String enter = newPassword.getText ();
        if (enter.isEmpty ())
            showAlertEmpty ();
        else {
            editPassword ( enter, user.getLogin () );
            editSuccess ();
            toRedactUser ( new ActionEvent () );
            Sample.windowRedact.close();
        }
    }

    public void createNewLogin(ActionEvent actionEvent) throws IOException {
        String enter = newLogin.getText ();
        if (enter.isEmpty ())
            showAlertEmpty ();
        else {
            editLogin ( enter, user.getLogin () );
            editSuccess ();
            toRedactUser ( new ActionEvent () );
            Sample.windowRedact.close();
        }
    }

    public void createNewSurname(ActionEvent actionEvent) throws IOException {
        String enter = newSurname.getText ();
        if (enter.isEmpty ())
            showAlertEmpty ();
        else if (!checkCorrect ( enter ))
            warning ();
        else {
            editSurname ( enter, user.getLogin () );
            editSuccess ();
            toRedactUser ( new ActionEvent () );
            Sample.windowRedact.close();
        }
    }

    public void createNewName(ActionEvent actionEvent) throws IOException {
        String enter = newName.getText ();
        if (enter.isEmpty ())
            showAlertEmpty ();
        else if (!checkCorrect ( enter ))
            warning ();
        else {
            editName ( enter, user.getLogin () );
            editSuccess ();
            toRedactUser ( new ActionEvent () );
            Sample.windowRedact.close();
        }
    }

    public void createNewMail(ActionEvent actionEvent) throws IOException {
        String enter = newMail.getText ();
        if (enter.isEmpty ())
            showAlertEmpty ();
        else {
            editMail ( enter, user.getLogin () );
            editSuccess ();
            toRedactUser ( new ActionEvent () );
            Sample.windowRedact.close();
        }
    }

    public void showAlertEmpty() {
        Alert alert = new Alert ( Alert.AlertType.WARNING );
        alert.setTitle ( "Предупреждение" );
        alert.setHeaderText ( null );
        alert.setContentText ( "Вы не ввели новое значение!" );
        alert.showAndWait ();
    }

    public void editSuccess() {
        Alert alert = new Alert ( Alert.AlertType.INFORMATION );
        alert.setTitle ( null );
        alert.setHeaderText ( null );
        alert.setContentText ( "Редактирование произведено!" );
        alert.showAndWait ();
    }

    public void warning() {
        Alert alert = new Alert ( Alert.AlertType.ERROR );
        alert.setTitle ( "Ошибка" );
        alert.setHeaderText ( null );
        alert.setContentText ( "Некорректный ввод!" );
        alert.showAndWait ();
    }

    public void toRedactUser(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/adminActions/RedactUser.fxml"));
        MainClient.primaryStage.setScene(new Scene(root));
        MainClient.primaryStage.show();
    }
}
