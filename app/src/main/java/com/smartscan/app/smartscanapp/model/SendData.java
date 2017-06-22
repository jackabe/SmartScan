package com.smartscan.app.smartscanapp.model;

import android.graphics.Color;
import android.widget.Toast;

import com.smartscan.app.smartscanapp.ConnectThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Jack_Allcock on 22/06/2017.
 */

public class SendData {

    private JSONObject jsonObject;
    private String jsonStart;
    private String jsonEnd;
    private ConnectThread connectThread;

    public SendData(ConnectThread connectThread) {
        this.jsonObject = new JSONObject();
        this.connectThread = connectThread;
    }

    public void sendData(String jsonStart, String jsonEnd) {
        try {
            jsonObject.put(jsonStart, jsonEnd);
            try {
                connectThread.sendData(jsonObject);
            } catch (IOException e) {

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
