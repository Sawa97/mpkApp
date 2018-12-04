package controllers;

import client_server.Client;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.joda.time.DateTime;

public class TimePanelController {

    @FXML
    JFXDatePicker datePicker;
    @FXML
    JFXTimePicker timePicker;

    public void actualTimeHandler(){
        Client.CLIENT_INSTANCE.setActualTime(true);
        Client.CLIENT_INSTANCE.setSearchingTime(new DateTime());

        Stage stage = (Stage)datePicker.getScene().getWindow();
        stage.close();

    }

    public void okHandler(){

        if(datePicker.getValue()!=null&&timePicker.getValue()!=null){
            DateTime dt = new DateTime(datePicker.getValue().getYear(),datePicker.getValue().getMonthValue(),datePicker.getValue().getDayOfMonth(),timePicker.getValue().getHour(),timePicker.getValue().getMinute());
            Client.CLIENT_INSTANCE.setSearchingTime(dt);
            Client.CLIENT_INSTANCE.setActualTime(false);
        }

        Stage stage = (Stage)datePicker.getScene().getWindow();
        stage.close();

    }
}
