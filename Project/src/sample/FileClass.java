package sample;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileClass {
    FileChooser chooser =new FileChooser();
    private String s;
    File file;

    FileClass() throws FileNotFoundException {
    }
    FileClass(File file) throws FileNotFoundException {
        this.file = file;
        s="";
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            s = s + scanner.nextLine() + "\n";
        }
    }
    FileClass(String s){
        this.s = s;
    }
    File getFile(){
        return file;
    }
    String getString(){
        return s;
    }
    void setFile(File chosenfile) throws FileNotFoundException {
        this.file = chosenfile;
        s="";
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            s = s + scanner.nextLine() + "\n";
        }
    }
    void setString(String s){
        this.s=s;
    }
}
