package fr.projava.triangle.Objects;

import javafx.scene.control.Label;

public class MessageObject extends Label {
    private final String msg ;

    public MessageObject(String s, boolean sender,double width) {
        this.msg = s;
        this.setMaxWidth(width);
        if (sender){
            this.setStyle("-fx-background-color: #eedce5;-fx-text-fill: #000000 ;-fx-font-family: Arial;-fx-font-size: 12; -fx-wrap-text:true");
        } else {
            this.setStyle("-fx-background-color: #caccec;-fx-text-fill: #000000 ;-fx-font-family: Arial;-fx-font-size: 12; -fx-wrap-text:true");
        }
        this.setText(s);
    }

}
