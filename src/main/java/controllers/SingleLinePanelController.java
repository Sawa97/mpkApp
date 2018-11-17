package controllers;

import client_server.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.MalformedURLException;

public class SingleLinePanelController {

    @FXML
    Label numberLabel;

    @FXML
    AnchorPane pane;

    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    createPlan();
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }
            }
        });

        numberLabel.setText(Client.CLIENT_INSTANCE.getSearchedLine().getLineNumber());


    }


    public void createPlan() throws MalformedURLException {
        double actualY = 92;

        File file = new File("src/main/resources/images/Circle.png");
        Image image = new Image(file.toURI().toURL().toExternalForm());
        ImageView imageView;
        Label label;



        for(int i=0; i<Client.CLIENT_INSTANCE.getSearchedLine().getPlan().size();i++){

            //image part
            imageView = new ImageView(image);
            imageView.setFitHeight(17);
            imageView.setFitWidth(17);
            imageView.setX(14);
            imageView.setY(actualY);
            pane.getChildren().add(imageView);
            ////////////////////////////////////////


            //Label
            label = new Label();
            label.setLayoutY(actualY-5);
            label.setLayoutX(50);
            label.setText(Client.CLIENT_INSTANCE.getSearchedLine().getPlan().get(i).getBusStop().getBusStopName());
            pane.getChildren().add(label);
            //////////////////////////////////////////

            actualY+=40;
        }


    }



}
