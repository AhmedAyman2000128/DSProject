package com.example.project;

import com.example.project.Level1.*;
import com.example.project.Level1.Error.ErrorCheckAndCorrect;
import com.example.project.Level1.Error.ErrorDetect;
import com.example.project.Level1.compress_decompress.HuffmanEncoder;
import com.example.project.Level2.GraphRepresentation.OurGraph;
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

import java.io.*;
import java.net.URL;
import java.util.BitSet;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    FileClass file;//created by me to include the file and its string representation
    HuffmanEncoder encoder;
    FileChooser fileChooser;
    Undo_Redo_Fun undo_redo;
    boolean compressedFlag;
    HuffmanEncoder.EncodedData compressed;
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
        if(file==null){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Please press Choose Button");
            alert.show();
        }
        else{
            ErrorDetect e = new ErrorDetect(file.getString());
            e.checkerror();
            Alert alert;
            if(e.getErrorMsg().length()==0){
                alert = new Alert(Alert.AlertType.WARNING,"No error found !");
            }
            else{
                alert = new Alert(Alert.AlertType.WARNING,e.getErrorMsg());
            }
            alert.show();
        }
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
        if(file==null){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Please press Choose Button");
            alert.show();
        }
        else
        {
            ErrorCheckAndCorrect e = new ErrorCheckAndCorrect(file.getString());
            Indentation i = new Indentation(e.getXmlAfterCorrection());
            tBoxAfter.setText(i.getIntendedString());
            undo_redo.addString(tBoxAfter.getText());
        }
    }

    @FXML
    void convertToJson(MouseEvent event) {
        if(file==null){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Please press Choose Button");
            alert.show();
        }
        else {
            //correct if there is any error
            ErrorCheckAndCorrect e = new ErrorCheckAndCorrect(file.getString());
            //convert to Json
            Json json = new Json();
            json.JsonConverter(e.getXmlAfterCorrection());
            tBoxAfter.setText(json.getJsonstr());
            undo_redo.addString(tBoxAfter.getText());
        }
    }

    @FXML
    void minify(MouseEvent event) {
        if(file==null){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Please press Choose Button");
            alert.show();
        }
        else{
            ErrorDetect e = new ErrorDetect(file.getString());
            e.checkerror();
            if(e.getErrorMsg().length()!=0){
                Alert alert;
                alert = new Alert(Alert.AlertType.WARNING,"file containing errors cannot manify!");
                alert.show();
            }
            else {
                Minifying m = new Minifying(file.getString());
                tBoxAfter.setText(m.getManifiedString());
                undo_redo.addString(tBoxAfter.getText());
            }
        }
    }

    @FXML
    void compressFile(MouseEvent event) throws IOException {
        if(file!=null){
            FileChooser.ExtensionFilter extFilter1;
            extFilter1 = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().addAll(extFilter1);
            encoder = new HuffmanEncoder();
            compressed = encoder.compress(file.getString());
            File fileCompress = fileChooser.showSaveDialog(new Stage());
            if(fileCompress != null){
                compressedFlag = true;
                encoder.writeCompressed(compressed,fileCompress);
            }
            fileChooser.getExtensionFilters().clear();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING,"No file is Chosen");
            alert.show();
        }
    }

    @FXML
    void decompressFile(MouseEvent event) throws IOException {
        if(compressedFlag == true) {
            FileChooser.ExtensionFilter extFilter1;
            extFilter1 = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().addAll(extFilter1);
            File decompressFile = fileChooser.showOpenDialog(new Stage());
            fileChooser.getExtensionFilters().clear();
            if(decompressFile !=null){
                FileInputStream fis = new FileInputStream(decompressFile);
                byte[] b = new byte[fis.available()];
                int chars = fis.read(b);
                BitSet bs = BitSet.valueOf(b);
                String binaryString = "";
                for(int i = 0; i <= bs.size(); i++) {
                    if(bs.get(i)) {
                        binaryString += "1";
                    } else {
                        binaryString += "0";
                    }
                }
                String decompressed = encoder.decompress(new HuffmanEncoder.EncodedData(compressed.getRoot(), binaryString ));
                tBoxAfter.setText(decompressed);
                undo_redo.addString(tBoxAfter.getText());
                fis.close();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING,"No file chosen for decompress");
                alert.show();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING,"Must compress the file first");
            alert.show();
        }
    }

    @FXML
    void saveFile(MouseEvent event) {
        String k = tBoxAfter.getText();
        if(!k.equals("")){
            ErrorCheckAndCorrect f = new ErrorCheckAndCorrect(file.getString());
            Json json = new Json();
            json.JsonConverter(f.getXmlAfterCorrection());
            FileChooser.ExtensionFilter extFilter1;
            extFilter1 = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().addAll(extFilter1);
            File file2 = fileChooser.showSaveDialog(new Stage());
            fileChooser.getExtensionFilters().clear();
            if (file != null) {
                try {
                    // Create a PrintWriter to write to the file
                    PrintWriter writer = new PrintWriter(file2);
                    // Write the text to the file
                    writer.println(k);
                    // Close the PrintWriter
                    writer.close();
                } catch (Exception e) {
                }
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING,"Please use any function to create new file to save it!");
            alert.show();
        }
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
        if(file==null){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Please press Choose Button");
            alert.show();
        }
        else{
            OurGraph graph = new OurGraph(file.getString());
            if(analysisChoiceBox.getSelectionModel().getSelectedItem() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING,"Please choose type of Analysis");
                alert.show();
            }
            else if(analysisChoiceBox.getSelectionModel().getSelectedItem().equals("Most influncer")){
                String s = graph.get_most_influencer_user();
                restLoadFxml("sampleRestOfGraph.fxml","Most influncer",s);
            }
            else if(analysisChoiceBox.getSelectionModel().getSelectedItem().equals("Most active")){
                String s = graph.Mostactive();
                restLoadFxml("sampleRestOfGraph.fxml","Most active",s);
            }
            else if(analysisChoiceBox.getSelectionModel().getSelectedItem().equals("Mutual Followers")){
                FXMLLoader loader  = new FXMLLoader(getClass().getResource("sampleMutualFollower.fxml"));
                Parent root = loader.load();
                ControllerMutualFollower controller = loader.getController();
                controller.setGraph(graph);
                Stage stage = (Stage) analysis.getScene().getWindow();
                Scene scene = new Scene(root);
                stage. setResizable(false);
                stage.setTitle("Mutual Followers");
                stage.setScene(scene);
            }
            else if(analysisChoiceBox.getSelectionModel().getSelectedItem().equals("Suggest Followers")){
                String s = graph.SuggestionListForEach();
                restLoadFxml("sampleRestOfGraph.fxml","Suggest Followers",s);
            }
            else if(analysisChoiceBox.getSelectionModel().getSelectedItem().equals("post Search")){
                FXMLLoader loader  = new FXMLLoader(getClass().getResource("samplePostSearch.fxml"));
                Parent root = loader.load();
                ControllerPostSearch controller = loader.getController();
                controller.setGraph(graph);
                Stage stage = (Stage) analysis.getScene().getWindow();
                Scene scene = new Scene(root);
                stage. setResizable(false);
                stage.setTitle("post Search");
                stage.setScene(scene);
            }
        }
    }

    @FXML
    void showVisualization(MouseEvent event) throws IOException {
        if(file == null){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Please press Choose Button");
            alert.show();
        }
        else{
            Visualization v = new Visualization();
            v.visualize(new Stage(),file.getString());
        }
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
