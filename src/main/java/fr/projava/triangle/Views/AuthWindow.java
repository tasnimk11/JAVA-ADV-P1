package fr.projava.triangle.Views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public static void main(String [] args) {
        AuthWindow Auth =  new AuthWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Get Pseudo
        String p = new String(pseudo.getText());
        System.out.println("pseudo is : " + p);
        if(e.getActionCommand() == "Sign in"){
            //sing in
            message.setText("signing in ...");
        } else if (e.getActionCommand() == "Sign up"){
            //sign up
            message.setText("signing up ...");
        } else {
            message.setText("nothing ...");
        }



    }
}
