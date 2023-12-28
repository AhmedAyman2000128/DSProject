package com.example.project;

import com.example.project.Level2.GraphRepresentation.OurGraph;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class ControllerMutualFollower implements Initializable {
    OurGraph graph;
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
    void setGraph(OurGraph graph){
        this.graph = graph;
        Vector<Integer>ids=graph.GetIDs();
        for(int i=0;i<ids.size();i++){
            firstUserBox.getItems().add(ids.get(i));
            secondUserBox.getItems().add(ids.get(i));
        }
    }

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
        if(firstUserBox.getSelectionModel().getSelectedItem() == null || secondUserBox.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Please choose two users");
            alert.show();
        }
        else{
            String s = graph.getMutualFollowers(graph.getUserFromId(firstUserBox.getSelectionModel().getSelectedItem()),graph.getUserFromId(secondUserBox.getSelectionModel().getSelectedItem()));
            resultArea.setText(s);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultArea.setEditable(false);
    }
}
