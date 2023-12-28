package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPostSearch implements Initializable {


    @FXML
    private Button exitBtn;

    @FXML
    private TextField fieldSearch;

    @FXML
    private TextArea resultArea;

    @FXML
    private Button searchBtn;

    @FXML
    void search(MouseEvent event) {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultArea.setEditable(false);
    }
}
