package com.smartscan.app.smartscanapp.Model;

/**
 * Created by Jack_Allcock on 20/06/2017.
 */

public enum Option {

    // The BELOW are main OPTIONS.
    STATE("Power Options", "Turn on and off, view battery.."),
    SCENES("Scenes", "Create and change light scenes"),
    MOTION("Motion", "Adjust the light motion settings"),
    INTENSITY("Intensity", "Alter intensity of the light"),
    STATUS("Status", "Monitor light status"),
    ADDRESSES("Addresses", "Change light addresses, E.g. group, building.."),
    TEMPLATES("Templates", "Attach pre-defined settings to light.."),
    SYSTEM("System Testing", "Test functionality of the light"),
    MISCELLANEOUS("Miscellaneous", "Extra functions not listed in the options above"),

    // These BELOW are all actions related to STATE
    TURNOFF("Turn The Fitting Off", "Clicking here will turn the device off"),
    TURNON("Turn the Fitting On", "Clicking here will turn the device on"),
    ENABLE("Enable Fitting", "Enable the functionality of the device"),
    DISABLE("Disable Fitting", "Disable the functionality of the device"),

    // These BELOW are all actions for templates
    TEMPLATEPOWER("Turn on device", "Switching this will turn on and off the fitting"),
    TEMPLATEENABLED("Enable Device", "Switching this will enable and disable the device");

    private final String optionName;
    private final String optionDesc;

    Option(String optionName, String desc) {
        this.optionDesc = desc;
        this.optionName = optionName;
    }

    public String getOptionName() {
        return optionName;
    }

    public String getOptionDesc() {
        return optionDesc;
    }

}

