package com.smartscan.app.smartscanapp.Model;

import com.smartscan.app.smartscanapp.ConnectThread;

import java.util.List;

/**
 * Created by Jack_Allcock on 20/06/2017.
 */

public enum DataCommand {

    commandOn("I1", "0BFF", "8B FF"),
    commandOff("I1", "0B00", "8B 00"),
    commandEnable("I1", "06C0", "86 C0"),
    commandDisable("I1", "0600", "86 00");

    private final String commandStart;
    private final String commandReturn;
    private final String commandEnd;
    private ConnectThread connectThread;

    DataCommand(String commandStart, String commandEnd, String commandReturn) {
        this.commandStart = commandStart;
        this.commandEnd = commandEnd;
        this.commandReturn = commandReturn;
    }

    public String getCommandEnd() {
        return commandEnd;
    }

    public String getCommandStart() {
        return commandStart;
    }

    public String getCommandReturn() {
        return commandReturn;
    }

}



