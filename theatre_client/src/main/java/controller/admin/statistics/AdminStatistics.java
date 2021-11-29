package controller.admin.statistics;

import controller.InteractionWithProgInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import statistics.Statistics;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static client.Client.getStatistics;

public class AdminStatistics extends InteractionWithProgInterface implements Initializable {

    public TableView<Statistics> Statistics;
    public TableColumn<Statistics, String> titleS;
    public TableColumn<Statistics, LocalDate> date;
    public TableColumn<Statistics, Integer> goal;
    public TableColumn<Statistics, Integer> result;
    public TableColumn<Statistics, Integer> numOfShows;

    public BarChart<String, Integer> bc;

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
        numOfShows.setCellValueFactory(new PropertyValueFactory<Statistics, Integer>("numberOfShows"));
        goal.setCellValueFactory(new PropertyValueFactory<Statistics, Integer>("goal"));
        result.setCellValueFactory(new PropertyValueFactory<Statistics, Integer>("result"));
        Statistics.setItems(nSeances);

        Statistics.setEditable(true);

        ArrayList<Statistics> statistics = null;
        XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
        XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
        XYChart.Series<String, Integer> series3 = new XYChart.Series<>();
        try {
            statistics = getStatistics();
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < statistics.size() - 2; i = i + 3) {
            series1.getData()
                    .add(new XYChart.Data<String, Integer>(statistics.get(i).getTitle(),
                            statistics.get(i).getResult()));
            series2.getData().add(new XYChart.Data<String, Integer>(statistics.get(i + 1).getTitle(),
                    statistics.get(i + 1).getResult()));
            series3.getData().add(new XYChart.Data<String, Integer>(statistics.get(i + 2).getTitle(),
                    statistics.get(i + 2).getResult()));
        }
        bc.getData().add(series1);
        bc.getData().add(series2);
        bc.getData().add(series3);
        series3.setName("3 мес. назад");
        series1.setName("Прошлый месяц");
        series2.setName("2 мес. назад");
    }
}
