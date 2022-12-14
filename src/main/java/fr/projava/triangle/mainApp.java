package fr.projava.triangle;

import fr.projava.triangle.Controllers.DatabaseController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class mainApp extends Application {
    @Override
    public void start(Stage stage) throws SQLException, IOException {
        //DB
        DatabaseController DB = new DatabaseController();

        //Authentication Window
        FXMLLoader fxmlLoader = new FXMLLoader(mainApp.class.getResource("/AuthWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 779, 450);
        stage.setTitle("Triangle");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
