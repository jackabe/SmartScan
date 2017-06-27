package com.smartscan.app.smartscanapp.Model;

import java.util.ArrayList;

/**
 * Created by Jack_Allcock on 26/06/2017.
 */

public class TemplateOption {

    private DataCommand command;
    private Option optionName;
    private int requested;
    private boolean sent;

    public TemplateOption(Option optionName, int requested, boolean sent, DataCommand command) {
        this.optionName = optionName;
        this.requested = requested;
        this.sent = sent;
        this.command = command;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public void setRequested(int requested) {
        this.requested = requested;
    }

    public Option getOptionName() {
        return optionName;
    }

    public void setOptionName(Option optionName) {
        this.optionName = optionName;
    }

    public DataCommand getCommand() {
        return command;
    }

    public void setCommand(DataCommand command) {
        this.command = command;
    }

    public int getRequested() {
        return requested;
    }
}
