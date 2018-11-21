package controllers;

import client_server.Client;
import client_server.data.WayOfTravel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class SearchPanelController implements ControllersHandlers {
    @FXML
    private JFXTextField searchStart;

    @FXML
    private JFXTextField searchStop;

    @FXML
    private JFXButton changeButton;

    @FXML
    private Text text;

    @FXML
    ImageView timeImage;

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
        String temp = searchStart.getText();
        searchStart.setText(searchStop.getText());
        searchStop.setText(temp);

    }

    public void optionHandler(){
        this.newStage("optionPanel.fxml", true, "", StageStyle.UNDECORATED);
    }

    public void timeHandler(){
        this.newStage("timePanel.fxml", true, "", StageStyle.UNDECORATED);

    }

    public void startSearchHandler(){
        Client.CLIENT_INSTANCE.setEndStation(searchStop.getText());
        Client.CLIENT_INSTANCE.setStartStation(searchStart.getText());
    }

}
