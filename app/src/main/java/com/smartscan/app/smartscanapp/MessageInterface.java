package com.smartscan.app.smartscanapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Jack_Allcock on 19/06/2017.
 */

public class MessageInterface {

    private String message;
    private static MessageInterface instance = new MessageInterface();
    private Message msg =  new Message();
    private Handler handler;
    private Bundle bundle;

    public MessageInterface() {

    }

    public static MessageInterface getInstance() {
        return instance;
    }

    public void sendData(final String str) {
        bundle = new Bundle();
        handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                message = str;
                if (!message.isEmpty()){
                    Log.i("Interface", "Message Revieved in Interface");
                    bundle.putString("Message", message );
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }
        });
    }

    public String getData() {
        return message;
    };

}
