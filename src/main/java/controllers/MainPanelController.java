package controllers;

import client_server.data.InformationFromPanel;
import com.jfoenix.controls.JFXButton;
import internet.InternetConnection;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
                search.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        Platform.exit();
                    }
                });
            }
        });


    }

    public void scheduleHandler() throws Exception {


        CLIENT_INSTANCE.setInformationFromPanel(InformationFromPanel.shedule);
        loadData();
        this.newStage("busStopListPanel.fxml", false, "Rozkład Przystanków", StageStyle.DECORATED);

    }

    public void searchHandler() {

        this.newStage("searchPanel.fxml", false, "Wyszkuwanie Połączeń", StageStyle.DECORATED);
    }

    public void lineHandler() throws Exception {
    loadData();
        this.newStage("lineShedulePanel.fxml", false, "Rozkład Linii", StageStyle.DECORATED);


    }

    private void loadData() throws Exception {
        if (!CLIENT_INSTANCE.isGraph()) {
            CLIENT_INSTANCE.setRequest("GETGRAPH");
        }
    }



}
