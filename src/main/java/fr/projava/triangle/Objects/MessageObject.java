package fr.projava.triangle.Objects;

import javafx.scene.control.Label;

public class MessageObject extends Label {
    private final String msg ;

    public MessageObject(String s, boolean sender,double width) {
        this.msg = s;
        this.setMaxWidth(width);
        if (sender){
            this.setStyle("-fx-background-color: #eedce5;-fx-text-fill: #000000 ;-fx-font-family: Arial;-fx-font-size: 12; -fx-wrap-text:true");
            this.setMaxSize(370, 60); //TODO : Max height
            this.setMinSize(370, 60); //TODO : Min height
            this.setPrefSize(370, 60); //TODO : Pref height
        } else {
            this.setStyle("-fx-background-color: #caccec;-fx-text-fill: #000000 ;-fx-font-family: Arial;-fx-font-size: 12; -fx-wrap-text:true");
            this.setMaxSize(370, 60);
            this.setMinSize(370, 60);
            this.setPrefSize(370, 60);
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
    private int calcHeight(){

        return 0;
    }
}
