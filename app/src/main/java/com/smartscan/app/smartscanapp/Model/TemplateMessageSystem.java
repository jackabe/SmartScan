package com.smartscan.app.smartscanapp.Model;

/**
 * Created by Jack_Allcock on 21/06/2017.
 */

public class TemplateMessageSystem {

    private volatile String message;

    public TemplateMessageSystem(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
