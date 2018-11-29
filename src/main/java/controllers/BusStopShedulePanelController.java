package controllers;

import client_server.Client;
import controllers.data.BusStopShedule;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;

import java.util.Comparator;

public class BusStopShedulePanelController{

    @FXML
    Label busLabel;

    @FXML
    ScrollPane scrollPane;

    private AnchorPane anchorPane;
    //private List<BusStopShedule> list = new ArrayList<>();

    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                busLabel.setText(Client.CLIENT_INSTANCE.getSearchedBusStop().getBusStopName());
                createScrollPaneContent();

            }
        });
    }


    public void createScrollPaneContent(){
        anchorPane = new AnchorPane();
        anchorPane.getStyleClass().add("pane");
        scrollPane.setFitToWidth(true);
        Client.CLIENT_INSTANCE.getListBusStopShedule().sort(new Comparator<BusStopShedule>() {
            @Override
            public int compare(BusStopShedule o1, BusStopShedule o2) {
                return Integer.compare(o1.getTimeToLeave(),o2.getTimeToLeave());
            }
        });
        createButtons(anchorPane);
        scrollPane.setContent(anchorPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setBorder(Border.EMPTY);
    }

    public void createButtons(AnchorPane anchorPane){


        double actualY = 25;
        AnchorPane centerPane;
        AnchorPane topPane;
        Label numLabel;
        Label destination;
        Label timeToLeave;
        Label busStop;
        Label time;



        ////////////////////////

        for(BusStopShedule bus : Client.CLIENT_INSTANCE.getListBusStopShedule()) {

            centerPane = new AnchorPane();
            topPane = new AnchorPane();
            numLabel = new Label();
            destination = new Label();
            busStop = new Label();
            timeToLeave = new Label();
            time = new Label();


            centerPane.getStyleClass().add("listCenterPane");
            topPane.getStyleClass().add("listTopPane");
            centerPane.setPrefSize(350, 100);
            centerPane.setLayoutX(10);
            centerPane.setLayoutY(actualY);
            topPane.setPrefHeight(30);
            topPane.setLayoutY(-15);
            topPane.setLayoutX(15);
            //////////////////////////////


            numLabel.setText(bus.getLine().getLineNumber());
            numLabel.setLayoutX(0);
            numLabel.setLayoutY(0);
            numLabel.setPrefSize(60, 20);
            topPane.getChildren().add(numLabel);
            //////////////////////////


            destination.getStyleClass().add("destinationLabel");
            destination.setText(bus.getLastStation() + "     ");
            destination.setLayoutY(2);
            destination.setLayoutX(70);
            topPane.getChildren().add(destination);
            ////////////////////////


            busStop.setLayoutX(15);
            busStop.setLayoutY(35);
            busStop.setText(Client.CLIENT_INSTANCE.getSearchedBusStop().getBusStopName());
            busStop.getStyleClass().add("busStopLabel");
            centerPane.getChildren().add(busStop);
            /////////////////////////


            timeToLeave.setLayoutX(15);
            timeToLeave.setLayoutY(75);

            //wyświetlanie czasu do odjazdu

            int hours = bus.getTimeToLeave()/60;
            int minutes = bus.getTimeToLeave()%60;
            if(hours<1) {
                timeToLeave.setText("Odjazd za " + bus.getTimeToLeave() + " min");
            }
            else {
                timeToLeave.setText("Odjazd za "+ hours+"h "+minutes+" min");
            }
            timeToLeave.getStyleClass().add("timeToLeaveLabel");
            centerPane.getChildren().add(timeToLeave);
            ///////////////////////////////

            time.setLayoutX(300);
            time.setLayoutY(35);
            if(bus.getTime().getMinuteOfHour()<10){
                time.setText(Integer.toString(bus.getTime().getHourOfDay())+":"+"0"+Integer.toString(bus.getTime().getMinuteOfHour()));

            }
            time.setText(Integer.toString(bus.getTime().getHourOfDay())+":"+Integer.toString(bus.getTime().getMinuteOfHour()));
            time.getStyleClass().add("busStopLabel");
            centerPane.getChildren().add(time);


            centerPane.getChildren().add(topPane);
            anchorPane.getChildren().add(centerPane);
            actualY += 140;
        }


    }
}
