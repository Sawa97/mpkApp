package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public interface ControllersHandlers {
    default void newStage(String resourceName, boolean onTop, String title,StageStyle stageStyle ){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(resourceName));
        try {
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(stageStyle);

            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setAlwaysOnTop(onTop);
            stage.show();

        } catch (IOException e) {
            e.getMessage();
        }
    }
}
