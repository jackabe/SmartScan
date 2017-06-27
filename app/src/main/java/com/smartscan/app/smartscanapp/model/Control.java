package com.smartscan.app.smartscanapp.Model;

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

    public ArrayList<Option> populateBasicIR() {
        options.add(Option.TYPE);
        options.add(Option.MOTION);
        options.add(Option.INTENSITY);
        options.add(Option.BURN);
        options.add(Option.MIN);
        options.add(Option.START);
        options.add(Option.POWER);
        options.add(Option.BRIGHT);
        options.add(Option.PIRSENS);
        options.add(Option.PIRENABLE);
        options.add(Option.VACANT);
        options.add(Option.SEC);
        options.add(Option.TDELAY);
        return options;
    }

    public ArrayList<Option> populateActions() {
        actions.add(Option.TURNON);
        actions.add(Option.TURNOFF);
        actions.add(Option.ENABLE);
        actions.add(Option.DISABLE);
        return actions;
    }

    public ArrayList<Option> populateTemplateActions() {
        actions.add(Option.TEMPLATEPOWER);
        actions.add(Option.TEMPLATEENABLED);
        return actions;
    }

    public ArrayList<Option> populateAddressActions() {
        actions.add(Option.BUILDING);
        actions.add(Option.GROUP);
        return actions;
    }

    public ArrayList<Option> populateSceneActions() {
        actions.add(Option.SCENESENABLE);
        actions.add(Option.SCENE1);
        actions.add(Option.SCENE2);
        actions.add(Option.SCENE3);
        actions.add(Option.SCENE4);
        return actions;
    }
}
