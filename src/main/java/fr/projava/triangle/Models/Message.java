package fr.projava.triangle.Models;

import java.sql.Date;

public class Message {
    private final boolean sender;
    private final String message;
    private Date sent_at;

    public Message(boolean sender, String message, Date sent_at) {
        this.sender=sender;
        this.message=message;
        this.sent_at=sent_at;

    }

    public Message(boolean sender, String message) {
        this.sender=sender;
        this.message=message;
    }

    public boolean isSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public Date getSent_at() {
        return sent_at;
    }
}
