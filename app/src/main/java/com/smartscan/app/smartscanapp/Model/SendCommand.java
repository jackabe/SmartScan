package com.smartscan.app.smartscanapp.Model;

import com.smartscan.app.smartscanapp.ConnectThread;

/**
 * Created by Jack_Allcock on 23/06/2017.
 */

public class SendCommand {

    private ConnectThread connectThread;
    private SendData sendData;

    public SendCommand(ConnectThread connectThread) {
        this.connectThread = connectThread;
    }

    public void sendCommand(DataCommand command) {
        String start = command.getCommandStart();
        String end = command.getCommandEnd();
        sendData = new SendData(connectThread);
        sendData.sendData(start, end);
    }
}
