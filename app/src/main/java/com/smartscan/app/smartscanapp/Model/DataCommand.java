package com.smartscan.app.smartscanapp.Model;

import com.smartscan.app.smartscanapp.Model.Menus.Option;

/**
 * Created by Jack_Allcock on 20/06/2017.
 */

public enum DataCommand {

    // Commands for PIR State and Mode and Sensitivity
    commandOn(Option.TEMPLATEPOWER, "I1", "0BFF", "Received 8B FF"),
    commandOff(Option.TEMPLATEPOWER, "I1", "0B00", "Received 8B 00"),
    commandEnable(Option.TEMPLATEENABLED, "I1", "06C0", "Received 86 C0"),
    commandDisable(Option.TEMPLATEENABLED, "I1", "0600", "Received 86 00"),
    commandSensitivityMax(Option.PIRSENS,"I1", "1700", "Received 97 00"),
    commandSensitivity5(Option.PIRSENS,"I1", "1701", "Received 97 01"),
    commandSensitivity4(Option.PIRSENS,"I1", "1702", "Received 97 02"),
    commandSensitivity3(Option.PIRSENS,"I1", "1705", "Received 97 05"),
    commandSensitivity2(Option.PIRSENS,"I1", "170C", "Received 97 0C"),
    commandSensitivity1(Option.PIRSENS,"I1", "1719", "Received 97 19"),
    commandSensitivityMin(Option.PIRSENS,"I1", "177D", "Received 97 7D"),

    // Commands for Light Level Delay Time

    commandDelay30S(Option.TDELAY,"I1", "0702", "Received 87 02"),
    commandDelay1(Option.TDELAY,"I1", "1704", "Received 87 04"),
    commandDelay2(Option.TDELAY,"I1", "1708", "Received 87 08"),
    commandDelay3(Option.TDELAY,"I1", "170C", "Received 87 0C"),
    commandDelay5(Option.TDELAY,"I1", "1714", "Received 87 14"),
    commandDelay10(Option.TDELAY,"I1", "1728", "Received 87 28"),
    commandDelay15(Option.TDELAY,"I1", "173C", "Received 87 3C"),
    commandDelay20(Option.TDELAY,"I1", "1750", "Received 87 50"),
    commandDelay30(Option.TDELAY,"I1", "1778", "Received 87 78"),
    commandDelay45(Option.TDELAY,"I1", "17B4", "Received 87 B4"),
    commandDelay1H(Option.TDELAY,"I1", "17F0", "Received 87 F0"),
    commandDelay2H(Option.TDELAY,"I1", "17F1", "Received 87 F1"),
    commandDelay3H(Option.TDELAY,"I1", "17F2", "Received 87 F2"),
    commandDelay4H(Option.TDELAY,"I1", "17F3", "Received 87 F3"),
    commandDelay5H(Option.TDELAY,"I1", "17F4", "Received 87 F4"),
    commandDelay6H(Option.TDELAY,"I1", "17F5", "Received 87 F5"),
    commandDelay7H(Option.TDELAY,"I1", "17F6", "Received 87 F6"),
    commandDelay8H(Option.TDELAY,"I1", "17F7", "Received 87 F7"),
    commandDelay9H(Option.TDELAY,"I1", "17F8", "Received 87 F8"),
    commandDelay10H(Option.TDELAY,"I1", "17F9", "Received 87 F9"),
    commandDelayCont(Option.TDELAY,"I1", "17FF", "Received 87 FF");


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



