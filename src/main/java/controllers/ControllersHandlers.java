package controllers;

import client_server.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public interface ControllersHandlers {
    default Stage newStage(String resourceName, boolean onTop, String title,StageStyle stageStyle ){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(resourceName));
        try {
            Parent root =  fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(stageStyle);
            stage.setResizable(false);

            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setAlwaysOnTop(onTop);

            if(resourceName.equals("singleLinePanel.fxml")){
                stage.setMaxHeight(calculateSize());
            }

            stage.show();
            return stage;

        } catch (IOException e) {
            e.getMessage();
        }
        return null;
    }

    default double calculateSize(){
        return 92+(Client.CLIENT_INSTANCE.getSearchedLine().getBusStopList().size()*40)+40;
        //92 odległóść od góry, 40 przestrzeń zajmowana przez jeden element, 40 odległość od dołu
    }

}
