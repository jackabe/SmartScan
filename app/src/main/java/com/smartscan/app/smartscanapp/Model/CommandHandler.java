package com.smartscan.app.smartscanapp.Model;

import android.util.Log;

/**
 * Created by Jack_Allcock on 23/06/2017.
 */

public class CommandHandler {

    private String message;

    public CommandHandler() {

    }

    public void setMessage(String message) {
        this.message = message;
    }


    public boolean handleCommand(DataCommand command) {
        Log.i("hi", message);
        if (message.contains(command.getCommandReturn())) {
            return true;
        }
        else {
            return false;
        }
    }
}
