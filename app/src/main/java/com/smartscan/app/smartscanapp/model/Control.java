package com.smartscan.app.smartscanapp.model;

import java.util.ArrayList;

/**
 * Created by Jack_Allcock on 20/06/2017.
 */

public class Control {

    private ArrayList<Option> options;

    public Control() {
        this.options = new ArrayList<>();
    }

    public ArrayList<Option> populateOptions() {
        options.add(Option.STATE);
        options.add(Option.MOTION);
        options.add(Option.SCENES);
        options.add(Option.INTENSITY);
        options.add(Option.STATUS);
        options.add(Option.ADDRESSES);

        return options;
    }
}
