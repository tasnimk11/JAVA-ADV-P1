package fr.projava.triangle;

import fr.projava.triangle.Controllers.AccountController;
import fr.projava.triangle.Controllers.DatabaseController;
import fr.projava.triangle.Controllers.ThreadController;
import fr.projava.triangle.Views.ChatWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class mainApp extends Application {
    @Override
    public void start(Stage stage) throws SQLException, IOException {
        //DB
        DatabaseController DB = new DatabaseController();
        //Thread Ctrl
        ThreadController TC = new ThreadController();
        //Authentication Window
        Parent root = FXMLLoader.load(mainApp.class.getResource("/AuthWindow.fxml"));
        Scene authScene = new Scene(root, 779, 450);
        stage.setTitle("Triangle");
        //stage.getIcons().add(new Image("src/main/resources/images/logo.png"));
        stage.getIcons().add(new Image(mainApp.class.getResourceAsStream("/images/logo.png")));
        stage.setScene(authScene);
        stage.show();

        stage.setOnCloseRequest(
                event -> {
                    event.consume();
                    if (!stage.getScene().equals(authScene)){
                        try {
                            AccountController.closeConnection();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    stage.close();
        });

    }

    public static void main(String[] args) {
        launch();
    }
}
