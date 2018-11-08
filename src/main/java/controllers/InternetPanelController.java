package controllers;

import com.jfoenix.controls.JFXButton;
import internet.InternetConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InternetPanelController implements InternetConnection {

    @FXML
    private JFXButton cancel;


    @FXML
    private Text error;

   public void cancelHandler(){
       Platform.exit();
   }

   public void refreshHandler(){
       Stage stage = (Stage) cancel.getScene().getWindow();
       if (this.netIsAvailable()){
           stage.close();
       }
       else {
           stage.close();
           stage.show();
       error.setText("Brak Internetu, Spróbuj ponownie!");

       }

   }

}
