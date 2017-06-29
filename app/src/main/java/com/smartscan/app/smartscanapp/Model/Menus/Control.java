package com.smartscan.app.smartscanapp.Model.Menus;

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

    public ArrayList<Option> populateControlMenu() {
        options.add(Option.IR);
        options.add(Option.RF);
        return options;
    }

    public ArrayList<Option> populateIRMenu() {
        options.add(Option.BASIC);
        options.add(Option.DOWNLOAD);
        options.add(Option.ECOMM);
        options.add(Option.DALIDRIVER);
        options.add(Option.ESTATUS);
        options.add(Option.ESMARTSCAN);
        options.add(Option.SMARTSCANSENSOR);
        options.add(Option.SMARTTR);
        options.add(Option.SYSTEMTESTS);
        options.add(Option.IRREMOTE);
        options.add(Option.MONITORS);
        options.add(Option.SCENEPARAM);
        return options;
    }

    public ArrayList<Option> populateBasicIR() {
        options.add(Option.POWER);
        options.add(Option.PIRMODE);
        options.add(Option.PIRSENS);
        options.add(Option.TDELAY);
        options.add(Option.SEC);
        options.add(Option.VACANT);
        options.add(Option.HOLD);
        options.add(Option.BRIGHT);
        options.add(Option.INTENSITY);
        options.add(Option.MOTION);
        options.add(Option.BURN);
        options.add(Option.MIN);
        options.add(Option.START);
        options.add(Option.OUTPUT);
        return options;
    }

    public ArrayList<Option> populatePowerOptions() {
        options.add(Option.TURNON);
        options.add(Option.TURNOFF);
        return options;
    }

    public ArrayList<Option> populateModeOptions() {
        options.add(Option.ENABLE);
        options.add(Option.DISABLE);
        return options;
    }

    public ArrayList<Option> populateSensitivityOptions() {
        options.add(Option.SENSMAX);
        options.add(Option.SENS5);
        options.add(Option.SENS4);
        options.add(Option.SENS3);
        options.add(Option.SENS2);
        options.add(Option.SENS1);
        options.add(Option.SENSMIN);
        return options;
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

    public ArrayList<Option> populateDelayActions() {
        options.add(Option.DELAY30S);
        options.add(Option.DELAY1);
        options.add(Option.DELAY2);
        options.add(Option.DELAY3);
        options.add(Option.DELAY5);
        options.add(Option.DELAY10);
        options.add(Option.DELAY15);
        options.add(Option.DELAY20);
        options.add(Option.DELAY30);
        options.add(Option.DELAY45);
        options.add(Option.DELAY1H);
        options.add(Option.DELAY2H);
        options.add(Option.DELAY3H);
        options.add(Option.DELAY4H);
        options.add(Option.DELAY5H);
        options.add(Option.DELAY6H);
        options.add(Option.DELAY7H);
        options.add(Option.DELAY8H);
        options.add(Option.DELAY9H);
        options.add(Option.DELAY10H);
        options.add(Option.DELAYCON);
        return options;
    }
}
