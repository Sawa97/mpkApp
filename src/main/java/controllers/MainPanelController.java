package controllers;

import com.jfoenix.controls.JFXButton;
import internet.InternetConnection;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import static client_server.Client.CLIENT_INSTANCE;

public class MainPanelController implements InternetConnection, ControllersHandlers {

    @FXML
    private JFXButton line;

    @FXML
    private JFXButton schedule;

    @FXML
    private JFXButton search;

    @FXML
    private Text loadLabel;


    public void initialize() {

        schedule.setFocusTraversable(false);
        search.setFocusTraversable(false);
        line.setFocusTraversable(false);

        if (!this.netIsAvailable()) {
            this.newStage("internetPanel.fxml", true, "Internet", StageStyle.UNDECORATED);

        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loadLabel.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        Platform.exit();
                    }
                });
            }
        });


    }

    public void scheduleHandler() {


    }

    public void searchHandler() {

        this.newStage("searchPanel.fxml", false, "Wyszkuwanie Połączeń", StageStyle.DECORATED);
    }

    public void lineHandler() throws Exception {
        loadLineData();
        this.newStage("lineShedulePanel.fxml", false, "Rozkład Linii", StageStyle.DECORATED);
        loadLabel.setText("");


    }

    private void loadLineData() throws Exception {
        loadLabel.setText("Loading...");
        if (CLIENT_INSTANCE.getAllLines().size() == 0) {
            CLIENT_INSTANCE.setRequest("GETLINES");
        }
    }


}
