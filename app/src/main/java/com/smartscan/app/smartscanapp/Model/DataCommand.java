package com.smartscan.app.smartscanapp.Model;

import com.smartscan.app.smartscanapp.ConnectThread;

import java.util.List;

/**
 * Created by Jack_Allcock on 20/06/2017.
 */

public enum DataCommand {

    commandOn(Option.TEMPLATEPOWER, "I1", "0BFF", "Received 8B FF"),
    commandOff(Option.TEMPLATEPOWER, "I1", "0B00", "Received 8B 00"),
    commandEnable(Option.TEMPLATEENABLED, "I1", "06C0", "Received 86 C0"),
    commandDisable(Option.TEMPLATEENABLED, "I1", "0600", "Received 86 00");

    private final Option commandOption;
    private final String commandStart;
    private final String commandEnd;
    private final String commandReturn;

    DataCommand(Option commandOption, String commandStart, String commandEnd, String commandReturn) {
        this.commandOption = commandOption;
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

    public Option getCommandOption() {
        return commandOption;
    }
}



