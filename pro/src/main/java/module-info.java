module com.example.graph {
    requires javafx.controls;
    requires javafx.fxml;
    requires gs.core;
    requires gs.ui.javafx;


    opens com.example.project to javafx.fxml;
    exports com.example.project;
    exports com.example.project.Level1;
    opens com.example.project.Level1 to javafx.fxml;
    exports com.example.project.Level2;
    opens com.example.project.Level2 to javafx.fxml;
    exports com.example.project.Level1.Error;
    opens com.example.project.Level1.Error to javafx.fxml;
    exports com.example.project.Level1.compress_decompress;
    opens com.example.project.Level1.compress_decompress to javafx.fxml;
    exports com.example.project.Level2.GraphRepresentation;
    opens com.example.project.Level2.GraphRepresentation to javafx.fxml;
}