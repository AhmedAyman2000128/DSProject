package com.example.project.Level1;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileClass {
    FileChooser chooser =new FileChooser();
    private String s;
    File file;

    public FileClass() throws FileNotFoundException {
    }

    public FileClass(File file) throws FileNotFoundException {
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

    public String getString(){
        return s;
    }

    public void setFile(File chosenfile) throws FileNotFoundException {
        this.file = chosenfile;
        s="";
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            s = s + scanner.nextLine() + "\n";
        }
    }

    public void setString(String s){
        this.s=s;
    }
}