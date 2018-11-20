package controllers;

import client_server.Client;
import data.Line;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

import java.util.List;


public class LineShedulePanelController implements ControllersHandlers{

    @FXML
    TextField searchLine;

    @FXML
    AnchorPane centerPane;

    @FXML
    Label loadingLabel;

    private double height;
    private double width;


    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {


                createLineButtons(Client.CLIENT_INSTANCE.getAllLines());

                centerPane.getScene().addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(event.getTarget() instanceof Text) {
                            Text t = (Text) event.getTarget();
                            Client.CLIENT_INSTANCE.setSearchedLine(t.getText());
                            newStage("singleLinePanel.fxml",false,"Rozkład linii", StageStyle.DECORATED);

                        }
                    }
                });
            }
        });

        searchLine.textProperty().addListener((observable, oldValue, newValue) -> {
            centerPane.getChildren().clear();
            if(newValue.equals("")||newValue.isEmpty()){
                createLineButtons(Client.CLIENT_INSTANCE.getAllLines());
            }
            else
            {
                createLineButtons(Client.CLIENT_INSTANCE.searchLines(newValue));
            }
        });

    }

    public void createLineButtons(List<Line> lines){

        height = centerPane.getHeight();
        width = centerPane.getWidth();

        double buttonHeight;
        double buttonWidth;
        double actualX = 0;
        double actualY = 0;

        buttonHeight = height/10;
        buttonWidth = width/5;

        Button btn;
        for(int i = 0; i< lines.size(); i++){
            if(width<actualX+buttonWidth){
                actualX=0;
                actualY+=buttonHeight;
            }
            btn = new Button();
            btn.setText(lines.get(i).getLineNumber());
            btn.setLayoutX(actualX);
            btn.setLayoutY(actualY);
            btn.setPrefSize(buttonWidth,buttonHeight);
            centerPane.getChildren().add(btn);

            actualX+=buttonWidth;


        }

    }
}
