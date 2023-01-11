package fr.projava.triangle.Views;

import fr.projava.triangle.Controllers.AccountController;
import fr.projava.triangle.Controllers.DatabaseController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class AuthWindow extends JFrame implements ActionListener {

    private JTextField pseudo;
    private JButton signIn, signUp;
    private JLabel message;

    public AuthWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,600);
        this.setTitle("Triangle - Messaging App");
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        //ENTER PSEUDO
        pseudo = new JTextField();
        pseudo.setBounds(300,200,300,40);
        pseudo.setText("Enter Pseudo");

        //BUTTONS
        signIn = new JButton("Sign in");
        signIn.setBounds(300,300,100,40);
        signUp = new JButton("Sign up");
        signUp.setBounds(500,300,100,40);

        //Events
        message = new JLabel(" ");
        message.setBounds(300,400,300,40);
        signUp.addActionListener(this);
        signIn.addActionListener(this);


        //ADD Elements to view
        this.add(pseudo);
        this.add(signIn);
        this.add(signUp);
        this.add(message);


        this.setVisible(true);
    }

    /*public static void main(String [] args) throws SQLException {
        DatabaseController DB = new DatabaseController();
        AuthWindow Auth =  new AuthWindow();
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            //Get Pseudo
            String p = new String(pseudo.getText());
            if (e.getActionCommand() == "Sign in") {
                String msg = AccountController.connectToAccount(p);
                message.setText(msg);
            } else if (e.getActionCommand() == "Sign up") {
                String msg = AccountController.newAccount(p);
                message.setText(msg);
            } else {
                message.setText("nothing ...");
            }


        } catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException | InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}