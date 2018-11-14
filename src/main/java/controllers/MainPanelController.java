package controllers;

import client_server.Client;
import com.jfoenix.controls.JFXButton;
import internet.InternetConnection;
import javafx.fxml.FXML;
import javafx.stage.StageStyle;

public class MainPanelController implements InternetConnection,ControllersHandlers{

    @FXML
    private JFXButton line;

    @FXML
    private JFXButton schedule;

    @FXML
    private JFXButton search;


    public void initialize(){

        if(!this.netIsAvailable()){
            this.newStage("internetPanel.fxml",true,"Internet", StageStyle.UNDECORATED);

        }
    }

    public void scheduleHandler(){


    }
    public void searchHandler(){

       this.newStage("searchPanel.fxml",false,"Wyszkuwanie Połączeń",StageStyle.DECORATED);
    }
    public void lineHandler() throws Exception{
        Client.CLIENT_INSTANCE.setRequest("GETLINES");
        System.out.println(Client.CLIENT_INSTANCE.getAllLines().get(0).getLineNumber());
        this.newStage("lineShedulePanel.fxml",false,"Rozkład Linii",StageStyle.DECORATED);


    }


}
