package controller.alerts;

import javafx.scene.control.Alert;

public class AdminAlerts {

    public static void showAlertNoSelected(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Предупреждение");
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.showAndWait();
    }

    public static void delSuccess(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.showAndWait();
    }

    public static void showAlertEmptySpect() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ОШИБКА");
        alert.setHeaderText("Справка");
        alert.setContentText("Значения неверны!");
        alert.showAndWait();
    }

    public static void showAlertSameLogin() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ОШИБКА");
        alert.setHeaderText("Справка");
        alert.setContentText("Учётная запись с таким логином уже существует!");
        alert.showAndWait();
    }

    public static void showAlertIncorrect(String str) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ОШИБКА");
        alert.setHeaderText("Справка");
        alert.setContentText(str);
        alert.showAndWait();
    }

    public static void showAlertSuccess(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Поздравляем");
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.showAndWait();
    }

    public static void showAlertEmpty() {
        Alert alert = new Alert ( Alert.AlertType.WARNING );
        alert.setTitle ( "Предупреждение" );
        alert.setHeaderText ( null );
        alert.setContentText ( "Вы не ввели новое значение!" );
        alert.showAndWait ();
    }

    public static void editSuccess() {
        Alert alert = new Alert ( Alert.AlertType.INFORMATION );
        alert.setTitle ( null );
        alert.setHeaderText ( null );
        alert.setContentText ( "Редактирование произведено!" );
        alert.showAndWait ();
    }

    public static void warning() {
        Alert alert = new Alert ( Alert.AlertType.ERROR );
        alert.setTitle ( "Ошибка" );
        alert.setHeaderText ( null );
        alert.setContentText ( "Некорректный ввод!" );
        alert.showAndWait ();
    }

}
