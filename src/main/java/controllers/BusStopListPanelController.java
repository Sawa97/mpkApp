package controllers;

import client_server.Client;
import data.BusStop;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

import java.util.List;

public class BusStopListPanelController implements ControllersHandlers{

    @FXML
    ScrollPane scrollPane;

    @FXML
    TextField searchLine;

    private AnchorPane anchorPane;

    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createScrollPaneContent();

                anchorPane.getScene().addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(event.getTarget() instanceof Text){
                            Text t = (Text) event.getTarget();
                            Client.CLIENT_INSTANCE.setSearchedBusStop(t.getText());
                            newStage("busStopShedulePanel.fxml",false,"Rozkład Przystanku", StageStyle.DECORATED);
                        }

                    }
                });


            }
        });

        searchLine.textProperty().addListener((observable, oldValue, newValue) -> {
            anchorPane.getChildren().clear();
            if(newValue.equals("")||newValue.isEmpty()){
                createBusStopButtons(Client.CLIENT_INSTANCE.getBusStops(),anchorPane);
            }
            else {
                createBusStopButtons(Client.CLIENT_INSTANCE.busStops(newValue),anchorPane);
            }
        });

    }

    public void createScrollPaneContent(){
        anchorPane = new AnchorPane();
        anchorPane.getStyleClass().add("pane");
        scrollPane.setFitToWidth(true);
        createBusStopButtons(Client.CLIENT_INSTANCE.getBusStops(), anchorPane);
        scrollPane.setContent(anchorPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setBorder(Border.EMPTY);
    }

    public void createBusStopButtons(List<BusStop> list, AnchorPane anchorPane){
        Button btn;
        double actualY = 0;
        for(int i=0; i<list.size(); i++){
            btn = new Button();
            btn.setText(list.get(i).getBusStopName());
            btn.setLayoutX(0);
            btn.setLayoutY(actualY);
            btn.setPrefSize(350,50);
            anchorPane.getChildren().add(btn);
            actualY+=50;
        }

    }


}
