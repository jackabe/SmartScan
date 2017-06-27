package com.smartscan.app.smartscanapp.Model;

import android.text.TextUtils;
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

        if (TextUtils.isEmpty(message)) {
            Log.i("test", "empty");
            return false;
        }

        if (message.contains(command.getCommandReturn())) {
            Log.i("test", "found");
            return true;
        }
        else {
            Log.i("test", "nope");
            return false;
        }
    }
}
