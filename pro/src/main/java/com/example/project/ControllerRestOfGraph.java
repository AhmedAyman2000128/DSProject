package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerRestOfGraph {

    @FXML
    private Button exitBtn;
    @FXML
    private Label labelFunction;
    @FXML
    private TextArea resultArea;
    void setLabels(String functionName,String restName){
        resultArea.setText(restName);
        labelFunction.setText(functionName);
    }

    @FXML
    void exitFunction(MouseEvent event) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        //Controller controller = loader.getController();
        Stage stage = (Stage)exitBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage. setResizable(false);
        stage.setTitle("XML Reader");
        stage.setScene(scene);
    }
}
