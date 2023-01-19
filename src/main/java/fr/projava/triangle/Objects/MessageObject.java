package fr.projava.triangle.Objects;

import javafx.scene.control.Label;

public class MessageObject extends Label {
    private final String msg ;
    private double maxLetters = 35; //35 "m"
    private double minWidth ; // quite standard
    private double minHeight = 17; //  1 line of message
    private double maxWidth ;

    public MessageObject(String s, boolean sender,double widthBox) {
        this.msg = s;
        this.maxWidth = widthBox-10;
        this.minWidth = widthBox-10;
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
    * given a message length,
    *   find out how many characters are needed per line
    *   calculates how many lines are needed
    *   returns the nbr of line needed * height of message
    * */
    private double calcHeight(int length){
        int a=(int)(length / maxLetters)+1;
        return minHeight*a;
    }

    private void setSizes(int length){
        this.setMaxSize(minWidth, calcHeight(length));
        this.setMinSize(minWidth, calcHeight(length));
        this.setPrefSize(minWidth, calcHeight(length));
    }
}
