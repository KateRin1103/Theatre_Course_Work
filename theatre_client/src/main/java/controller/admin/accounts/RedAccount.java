package controller.admin.accounts;

import account.User;
import controller.admin.InteractionWithProgInterface;
import controller.Sample;
import controller.alerts.AdminAlerts;
import javafx.event.ActionEvent;
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
            AdminAlerts.showAlertEmpty ();
        else {
            editPassword ( enter, user.getLogin () );
            AdminAlerts.editSuccess ();
            toRedactUser ( new ActionEvent () );
            Sample.windowRedact.close();
        }
    }

    public void createNewLogin(ActionEvent actionEvent) throws IOException {
        String enter = newLogin.getText ();
        if (enter.isEmpty ())
            AdminAlerts.showAlertEmpty ();
        else {
            editLogin ( enter, user.getLogin () );
            AdminAlerts.editSuccess ();
            toRedactUser ( new ActionEvent () );
            Sample.windowRedact.close();
        }
    }

    public void createNewSurname(ActionEvent actionEvent) throws IOException {
        String enter = newSurname.getText ();
        if (enter.isEmpty ())
            AdminAlerts. showAlertEmpty ();
        else if (!checkCorrect ( enter ))
            AdminAlerts.warning();
        else {
            editSurname ( enter, user.getLogin () );
            AdminAlerts.editSuccess ();
            toRedactUser ( new ActionEvent () );
            Sample.windowRedact.close();
        }
    }

    public void createNewName(ActionEvent actionEvent) throws IOException {
        String enter = newName.getText ();
        if (enter.isEmpty ())
            AdminAlerts.showAlertEmpty();
        else if (!checkCorrect ( enter ))
            AdminAlerts.warning();
        else {
            editName ( enter, user.getLogin () );
            AdminAlerts.editSuccess ();
            toRedactUser ( new ActionEvent () );
            Sample.windowRedact.close();
        }
    }

    public void createNewMail(ActionEvent actionEvent) throws IOException {
        String enter = newMail.getText ();
        if (enter.isEmpty ())
            AdminAlerts.showAlertEmpty();
        else {
            editMail ( enter, user.getLogin () );
            AdminAlerts.editSuccess();
            toRedactUser ( new ActionEvent () );
            Sample.windowRedact.close();
        }
    }


}
