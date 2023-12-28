package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMutualFollower implements Initializable {

    @FXML
    private TextArea resultArea;
    @FXML
    private Button exitBtn;
    @FXML
    private ChoiceBox<Integer> firstUserBox;
    @FXML
    private Button mutualFBtn;
    @FXML
    private ChoiceBox<Integer> secondUserBox;


    @FXML
    void exitWindow(MouseEvent event) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        //Controller controller = loader.getController();
        Stage stage = (Stage)exitBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage. setResizable(false);
        stage.setTitle("XML Reader");
        stage.setScene(scene);
    }

    @FXML
    void showFollowers(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultArea.setEditable(false);
    }
}
