package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class SearchController implements ControllersHandlers{
    @FXML
    private JFXTextField search1;

    @FXML
    private JFXTextField search2;

    @FXML
    private JFXButton changeButton;

    @FXML
    private Text text;

    private int countOfChange;
    private static final int MAX_CHANGE_COUNT = 3;


    public void searchHandler2(){


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

    public void searchHandler1(){

    }

    public void replaceHandler(){

    }
}
