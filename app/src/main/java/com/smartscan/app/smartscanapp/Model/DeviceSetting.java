package com.smartscan.app.smartscanapp.Model;

/**
 * Created by Jack_Allcock on 26/06/2017.
 */

public class DeviceSetting {

    private Option option;
    private int setting;
    private DataCommand command;

    public DeviceSetting(Option option, int setting, DataCommand command) {
        this.option = option;
        this.setting = setting;
        this.command = command;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public int getSetting() {
        return setting;
    }

    public void setSetting(int setting) {
        this.setting = setting;
    }

    public DataCommand getCommand() {
        return command;
    }

    public void setCommand(DataCommand command) {
        this.command = command;
    }
}
