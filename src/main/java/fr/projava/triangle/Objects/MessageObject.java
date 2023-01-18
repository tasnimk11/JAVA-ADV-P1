package fr.projava.triangle.Objects;

import javafx.scene.control.Label;

public class MessageObject extends Label {
    private final String msg ;
    private int maxLetters = 35; //35 "m"
    private int minWidth = 370; // quite standard
    private int minHeight = 17; //  1 line of message
    private int maxWidth = 290;

    public MessageObject(String s, boolean sender,double width) {
        this.msg = s;
        this.setMaxWidth(width);
        if (sender){
            this.setStyle("-fx-background-color: #eedce5;-fx-text-fill: #000000 ;-fx-font-family: Arial;-fx-font-size: 12; -fx-wrap-text:true");
            setSizes(s.length());
        } else {
            this.setStyle("-fx-background-color: #caccec;-fx-text-fill: #000000 ;-fx-font-family: Arial;-fx-font-size: 12; -fx-wrap-text:true");
            setSizes(s.length());
        }
        this.setText(s);
    }


    /*
    * TODO
    * given a message length,
    *   find out how many characters are needed per line
    *   calculates how many lines are needed
    *   returns the nbr of line needed * height of message
    * */
    private int calcHeight(int length){
        return minHeight*(length/maxLetters);
    }

    private void setSizes(int length){
        this.setMaxSize(minWidth, maxWidth);
        this.setMinSize(minWidth, minWidth);
        this.setPrefSize(minWidth, calcHeight(length));
    }
}
