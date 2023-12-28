package com.example.project;

import com.example.project.Level1.Error.ErrorCheckAndCorrect;
import com.example.project.Level1.FileClass;
import com.example.project.Level2.Undo_Redo_Fun;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    FileClass file;//created by me to include the file and its string representation
    FileChooser fileChooser;
    Undo_Redo_Fun undo_redo;
    boolean compressedFlag;
    @FXML
    private Button analysis;
    @FXML
    private ChoiceBox<String> analysisChoiceBox;
    @FXML
    private Button undoBtn;
    @FXML
    private Button redoBtn;
    @FXML
    private Button minifyBtn;
    @FXML
    private Button validateBtn;
    @FXML
    private Button identBtn;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Button chooseFileBtn;
    @FXML
    private Button jsonConvertBtn;
    @FXML
    private Button modifyBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button compressBtn;
    @FXML
    private Button decompressBtn;
    @FXML
    private TextArea tBoxAfter;
    @FXML
    private TextArea tBoxBefore;
    @FXML
    private Button visualizeBtn;

    @FXML
    void getFile(MouseEvent event) throws FileNotFoundException {
        if(choiceBox.getSelectionModel().getSelectedItem() == "File"){
            FileChooser.ExtensionFilter extFilter1;
            extFilter1 = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter1);
            File file2 = fileChooser.showOpenDialog(new Stage());
            if(file2!=null) {
                file = new FileClass(file2);
                file.setFile(file2);
                tBoxBefore.setText(file.getString());
            }
            fileChooser.getExtensionFilters().clear();
        }
        else if(choiceBox.getSelectionModel().getSelectedItem() == "Text"){
            if(tBoxBefore.getText().length() ==0){
                Alert alert=new Alert(Alert.AlertType.WARNING,"please enter your XML in textBox");
                alert.show();
            }
            else{
                file = new FileClass();
                file.setString(tBoxBefore.getText());
            }
            if(file!=null){}
        }
        else{
            Alert alert=new Alert(Alert.AlertType.WARNING,"please choose method of selection");
            alert.show();
        }
    }

    @FXML
    void validateXml(MouseEvent event) {

    }

    @FXML
    void eliminateError(MouseEvent event) {
        if(file==null){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Please press Choose Button");
            alert.show();
        }
        else {
            ErrorCheckAndCorrect s = new ErrorCheckAndCorrect(file.getString());
            tBoxAfter.setText(s.getXmlAfterCorrection());
            undo_redo.addString(s.getXmlAfterCorrection());
            //if there are any errors in file
            if(s.getErrorMessage().length() != 0){
                Alert alert = new Alert(Alert.AlertType.WARNING, s.getErrorMessage());
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING, "No errors found !");
                alert.show();
            }
        }
    }

    @FXML
    void indent(MouseEvent event) {

    }

    @FXML
    void convertToJson(MouseEvent event) {

    }

    @FXML
    void minify(MouseEvent event) {

    }

    @FXML
    void compressFile(MouseEvent event) throws IOException {

    }

    @FXML
    void decompressFile(MouseEvent event) throws IOException {

    }

    @FXML
    void saveFile(MouseEvent event) {

    }

    @FXML
    void undoFun(MouseEvent event) {
        if(tBoxAfter.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING,"No more undo");
            alert.show();
        }
        else{
            String s = undo_redo.undo();
            tBoxAfter.setText(s);
        }
    }

    @FXML
    void redoFun(MouseEvent event) {
        String s = undo_redo.redo();
        if(s.equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING,"No more redo");
            alert.show();
        }
        else{
            tBoxAfter.setText(s);
        }
    }
    //////////level2//////////
    @FXML
    void showAnalysis(MouseEvent event) throws IOException {

    }

    @FXML
    void showVisualization(MouseEvent event) throws IOException {

    }
    private void restLoadFxml(String sampleName,String functionName,String restName) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource(sampleName));
        Parent root = loader.load();
        ControllerRestOfGraph controller = loader.getController();
        controller.setLabels(functionName,restName);
        Stage stage = (Stage) analysis.getScene().getWindow();
        Scene scene = new Scene(root);
        stage. setResizable(false);
        stage.setTitle(functionName);
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBox.getItems().addAll("File","Text");
        analysisChoiceBox.getItems().addAll("Most influncer","Most active","Mutual Followers","Suggest Followers","post Search");
        tBoxAfter.setEditable(false);
        undo_redo = new Undo_Redo_Fun();
        fileChooser = new FileChooser();
        compressedFlag = false;
        FileChooser.ExtensionFilter extFilter1 ,extFilter2;
        extFilter1 = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        extFilter2 = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().addAll(extFilter1,extFilter2);
    }
}
