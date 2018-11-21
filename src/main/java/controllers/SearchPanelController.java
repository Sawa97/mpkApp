package controllers;

import client_server.Client;
import client_server.data.InformationFromPanel;
import client_server.data.WayOfTravel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import data.BusStop;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import static client_server.Client.CLIENT_INSTANCE;

public class SearchPanelController implements ControllersHandlers {

    @FXML
    private JFXButton changeButton;

    @FXML
    private Text text;

    @FXML
    ImageView timeImage;

    @FXML
    JFXTextField searchStart;

    @FXML
    JFXTextField searchStop;

    private int countOfChange;
    private static final int MAX_CHANGE_COUNT = 3;

    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                timeImage.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        Client.CLIENT_INSTANCE.setActualTime(true);
                        Client.CLIENT_INSTANCE.setWayOfTravel(WayOfTravel.fastest);
                        Platform.exit();
                    }
                });
            }
        });
    }

    public void changeHandler(){
        if(countOfChange<MAX_CHANGE_COUNT){
            countOfChange++;

        }
        else{
            countOfChange=0;
        }
        changeButton.setText(Integer.toString(countOfChange));
       new Thread(){
           @Override
           public void run() {
               try{
                   text.setText("Maksymalna liczba przesiadek "+countOfChange);
                   Thread.sleep(1500);
                   text.setText("");
               }catch (InterruptedException e){
                   e.getMessage();
               }
           }
       }.start();

    }


    public void replaceHandler(){
            BusStop temp = Client.CLIENT_INSTANCE.getStartStation();
            Client.CLIENT_INSTANCE.setStartStation(Client.CLIENT_INSTANCE.getEndStation());
            Client.CLIENT_INSTANCE.setEndStation(temp);
            String tempName = searchStart.getText();
            searchStart.setText(searchStop.getText());
            searchStop.setText(tempName);

    }

    public void searchStartHandler() throws Exception{
        if(CLIENT_INSTANCE.getBusStops().size() == 0){
            CLIENT_INSTANCE.setRequest("GETBUSSTOPS");
        }
        Client.CLIENT_INSTANCE.setInformationFromPanel(InformationFromPanel.start);
        Stage stage = this.newStage("busStopListPanel.fxml", false, "Przystanek Początkowy", StageStyle.DECORATED);
        stage.setOnHidden(event -> {
            searchStart.setText(Client.CLIENT_INSTANCE.getStartStation().getBusStopName());
        });


    }

    public void searchStopHandler() throws Exception{
        if(CLIENT_INSTANCE.getBusStops().size() == 0){
            CLIENT_INSTANCE.setRequest("GETBUSSTOPS");
        }
        Client.CLIENT_INSTANCE.setInformationFromPanel(InformationFromPanel.stop);

        Stage stage = this.newStage("busStopListPanel.fxml", false, "Przystanek Początkowy", StageStyle.DECORATED);
        stage.setOnHidden(event -> {
            searchStop.setText(Client.CLIENT_INSTANCE.getStartStation().getBusStopName());
        });

    }



    public void optionHandler(){
        this.newStage("optionPanel.fxml", true, "", StageStyle.UNDECORATED);
    }

    public void timeHandler(){
        this.newStage("timePanel.fxml", true, "", StageStyle.UNDECORATED);

    }

    public void startSearchHandler(){
        System.out.println(Client.CLIENT_INSTANCE.getEndStation().getBusStopName());
        System.out.println(Client.CLIENT_INSTANCE.getStartStation().getBusStopName());

    }

}
