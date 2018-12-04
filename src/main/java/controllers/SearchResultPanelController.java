package controllers;

import client_server.Client;
import client_server.data.Plan;
import data.Line;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;

import java.io.File;
import java.net.MalformedURLException;

public class SearchResultPanelController {

    @FXML
    AnchorPane mainPane;

    @FXML
    ScrollPane scrollPane;


    public void initialize() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    createPlan();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void createPlan() throws MalformedURLException {
        AnchorPane pane = new AnchorPane();
        pane.getStyleClass().add("listCenterPane");
        pane.setPrefWidth(350);


        //wielkość może tutaj będzie
        File fileCircle = new File("src/main/resources/images/Circle.png");
        File fileWalking = new File("src/main/resources/images/Walking_white.png");
        File fileBus = new File("src/main/resources/images/white_bus.png");
        javafx.scene.image.Image image;
        Line actualLine = new Line();
        int actualY = 0;
        if(Client.CLIENT_INSTANCE.getPlanList().size()==0){
            Label informLabel = new Label();
            informLabel.getStyleClass().add("destinationLabel");
            informLabel.setText("Nie znaleziono żadnego połączenia!");
            informLabel.setLayoutY(150);
            informLabel.setLayoutX(20);
            pane.getChildren().add(informLabel);
        }
        for (Plan plan : Client.CLIENT_INSTANCE.getPlanList()) {
            if (plan.getWalkdistance() == 0) {
                for (Line line : Client.CLIENT_INSTANCE.getGraph().getLineList()) {
                    if (line.getLineNumber().equals(plan.getLine())) {
                        actualLine = line;
                    }
                }
                AnchorPane leavePane = new AnchorPane();
                leavePane.getStyleClass().add("walkPane");
                leavePane.setLayoutX(0);
                leavePane.prefWidth(350);
                leavePane.setLayoutY(actualY);

                Label timeToLeave = new Label();
                timeToLeave.getStyleClass().add("busStopLabel");
                timeToLeave.setText(String.valueOf(plan.getWaitingTimeforBus())+" min       ");
                timeToLeave.setLayoutY(4);
                timeToLeave.setLayoutX(280);
                leavePane.getChildren().add(timeToLeave);


                pane.getChildren().add(leavePane);

                actualY+=30;


                image = new Image(fileBus.toURI().toURL().toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(24);
                imageView.setFitHeight(24);
                imageView.setLayoutX(8);
                imageView.setLayoutY(actualY+8);
                pane.getChildren().add(imageView);

                Label topLabel = new Label();
                topLabel.getStyleClass().add("destinationLabel");
                topLabel.setText(plan.getLine() + " " + actualLine.getEndStation());
                topLabel.setLayoutY(actualY+8);
                topLabel.setLayoutX(42);
                pane.getChildren().add(topLabel);

                Label minuteLabel = new Label();
                minuteLabel.getStyleClass().add("timeToLeaveLabel");
                minuteLabel.setLayoutX(280);
                minuteLabel.setLayoutY(actualY+12);
                minuteLabel.setText(String.valueOf(plan.getTime()) + " min");
                pane.getChildren().add(minuteLabel);


                image = new Image(fileCircle.toURI().toURL().toExternalForm());
                imageView = new ImageView(image);
                imageView.setFitWidth(18);
                imageView.setFitHeight(17);
                imageView.setLayoutX(13);
                imageView.setLayoutY(actualY+47);
                pane.getChildren().add(imageView);

                Label busStopLabel = new Label();
                busStopLabel.getStyleClass().add("busStopLabel");
                busStopLabel.setText(plan.getStartStation().getBusStopName());
                busStopLabel.setLayoutY(actualY+45);
                busStopLabel.setLayoutX(42);
                pane.getChildren().add(busStopLabel);

                Label timeLabel = new Label();
                busStopLabel.getStyleClass().add("busStopLabel");
                if(plan.getStartTime().getMinuteOfHour()<10){
                    timeLabel.setText(Integer.toString(plan.getStartTime().getHourOfDay()) + ":" +"0"+Integer.toString(plan.getStartTime().getMinuteOfHour()));

                }
                else {
                    timeLabel.setText(Integer.toString(plan.getStartTime().getHourOfDay()) + ":" + Integer.toString(plan.getStartTime().getMinuteOfHour()));
                }
                timeLabel.setLayoutY(actualY+45);
                timeLabel.setLayoutX(284);
                pane.getChildren().add(timeLabel);


                Label restLabel = new Label();
                restLabel.getStyleClass().add("timeToLeaveLabel");
                restLabel.setText("Pozostałe przystanki(" + plan.getCountofStation() + ")");
                restLabel.setLayoutY(actualY+77);
                restLabel.setLayoutX(42);
                pane.getChildren().add(restLabel);


                image = new Image(fileCircle.toURI().toURL().toExternalForm());
                imageView = new ImageView(image);
                imageView.setFitWidth(18);
                imageView.setFitHeight(17);
                imageView.setLayoutX(13);
                imageView.setLayoutY(actualY+107);
                pane.getChildren().add(imageView);

                busStopLabel = new Label();
                busStopLabel.getStyleClass().add("busStopLabel");
                busStopLabel.setText(plan.getEndStation().getBusStopName());
                busStopLabel.setLayoutY(actualY+105);
                busStopLabel.setLayoutX(42);
                pane.getChildren().add(busStopLabel);

                timeLabel = new Label();
                busStopLabel.getStyleClass().add("busStopLabel");
                if(plan.getEndTime().getMinuteOfHour()<10){
                    timeLabel.setText(Integer.toString(plan.getEndTime().getHourOfDay()) + ":" +"0"+Integer.toString(plan.getEndTime().getMinuteOfHour()));

                }
                else {
                    timeLabel.setText(Integer.toString(plan.getEndTime().getHourOfDay()) + ":" + Integer.toString(plan.getEndTime().getMinuteOfHour()));
                }
                timeLabel.setLayoutY(actualY+105);
                timeLabel.setLayoutX(284);
                pane.getChildren().add(timeLabel);
                actualY+=140;


            } else {
                AnchorPane walkPane = new AnchorPane();
                walkPane.getStyleClass().add("walkPane");
                walkPane.setLayoutX(0);
                walkPane.prefWidth(350);
                walkPane.setMaxWidth(351);
                walkPane.setLayoutY(actualY);

                //wielkość


                image = new Image(fileWalking.toURI().toURL().toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(18);
                imageView.setFitHeight(17);
                imageView.setLayoutX(14);
                imageView.setLayoutY(9);
                walkPane.getChildren().add(imageView);

                Label timeLabel = new Label();
                timeLabel.getStyleClass().add("timeToLeaveLabel");
                timeLabel.setText(plan.getWalkdistance() + "m(" + plan.getTime() + " min)  " + "                                                            ");
                timeLabel.setLayoutY(7);
                timeLabel.setLayoutX(50);
                walkPane.getChildren().add(timeLabel);

                pane.getChildren().add(walkPane);

                actualY+=30;



            }

        }
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setBorder(Border.EMPTY);
        scrollPane.setContent(pane);

    }
}
