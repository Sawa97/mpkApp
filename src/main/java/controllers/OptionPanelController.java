package controllers;

import client_server.Client;
import client_server.data.WayOfTravel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OptionPanelController {

    @FXML
    Button fastestButton;

    public void minDistanceButtonHandler(){
        Client.CLIENT_INSTANCE.setWayOfTravel(WayOfTravel.minDistance);
        Stage stage = (Stage)fastestButton.getScene().getWindow();
        stage.close();
    }

    public void minCountofChangeButtonHandler(){
        Client.CLIENT_INSTANCE.setWayOfTravel(WayOfTravel.minCountOfChanges);
        Stage stage = (Stage)fastestButton.getScene().getWindow();
        stage.close();
    }

    public void fastestButtonHandler(){
        Client.CLIENT_INSTANCE.setWayOfTravel(WayOfTravel.fastest);
        Stage stage = (Stage)fastestButton.getScene().getWindow();
        stage.close();
    }
}
