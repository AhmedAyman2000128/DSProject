package com.example.project;

import com.example.project.Level2.GraphRepresentation.OurGraph;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPostSearch implements Initializable {

    OurGraph graph;
    void setGraph(OurGraph graph){
        this.graph = graph;
    }
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
        if(fieldSearch.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Please enter word for search");
            alert.show();
        }
        else{
            String s = graph.searchPostsAndTopics(fieldSearch.getText());
            if(s.length()==0){
                s = "Not Found" ;
            }
            resultArea.setText(s);
        }
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
