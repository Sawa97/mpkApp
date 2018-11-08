package controllers;

import internet.InternetConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainPanelController implements InternetConnection {

    public void initialize(){

        if(!this.netIsAvailable()){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("internetPanel.fxml"));
            try {
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);

                stage.setTitle("Internet");
                stage.setScene(new Scene(root));
                stage.setAlwaysOnTop(true);
                stage.show();

            } catch (IOException e) {
                e.getMessage();
            }
        }
    }


}
