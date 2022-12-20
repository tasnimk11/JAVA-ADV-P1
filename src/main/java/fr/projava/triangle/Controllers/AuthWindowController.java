package fr.projava.triangle.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.UnknownHostException;
import java.sql.SQLException;

public class AuthWindowController {
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnSignUp;
    @FXML
    private TextField pseudoInput;
    @FXML
    private Label returnMessage;
    public void signIn(MouseEvent mouseEvent) throws UnknownHostException, SQLException, InterruptedException {
        String p = pseudoInput.getText();
        String msg = AccountController.connectToAccount(p);
        returnMessage.setText(msg);
    }

    public void signUp(MouseEvent mouseEvent) throws UnknownHostException, SQLException {
        String p = pseudoInput.getText();
        String msg = AccountController.newAccount(p);
        returnMessage.setText(msg);
    }
}
