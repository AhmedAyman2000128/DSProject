package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    FileClass file;//created by me to include the file and its string representation
    FileChooser fileChooser = new FileChooser();

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
    private TextArea tBoxAfter;
    @FXML
    private TextArea tBoxBefore;

    //choose file button on clicked action
    @FXML
    void getFile(MouseEvent event) throws FileNotFoundException {
        if(choiceBox.getSelectionModel().getSelectedItem() == "From File"){
            file = new FileClass();
            file.setFile(fileChooser.showOpenDialog(new Stage()));
            tBoxBefore.setText(file.getString());
        }
        else if(choiceBox.getSelectionModel().getSelectedItem() == "From text"){
            if(tBoxBefore.getText().length() ==0){
                Alert alert=new Alert(Alert.AlertType.WARNING,"please enter text in textBox");
                alert.show();
            }
            else{
                file = new FileClass();
                file.setString(tBoxBefore.getText());
            }
            if(file!=null)
                System.out.println(file.getString());
        }
        else{
            Alert alert=new Alert(Alert.AlertType.WARNING,"please choose method of selection");
            alert.show();
        }
    }

    @FXML
    void saveFile(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBox.getItems().addAll("From File","From text");
    }
}
