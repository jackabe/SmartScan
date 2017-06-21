package com.smartscan.app.smartscanapp.model;

import java.util.ArrayList;

/**
 * Created by Jack_Allcock on 20/06/2017.
 */

public class Control {

    private ArrayList<Option> options;
    private ArrayList<Option> actions;

    public Control() {
        this.options = new ArrayList<>();
        this.actions = new ArrayList<>();
    }

    public ArrayList<Option> populateOptions() {
        options.add(Option.STATE);
        options.add(Option.MOTION);
        options.add(Option.SCENES);
        options.add(Option.INTENSITY);
        options.add(Option.STATUS);
        options.add(Option.ADDRESSES);
        options.add(Option.TEMPLATES);
        options.add(Option.SYSTEM);
        options.add(Option.MISCELLANEOUS);

        return options;
    }

    public ArrayList<Option> populateActions() {
        actions.add(Option.TURNON);
        actions.add(Option.TURNOFF);
        actions.add(Option.ENABLE);
        actions.add(Option.DISABLE);
        return actions;
    }
}
