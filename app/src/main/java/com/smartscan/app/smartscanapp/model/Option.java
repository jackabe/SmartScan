package com.smartscan.app.smartscanapp.Model;

/**
 * Created by Jack_Allcock on 20/06/2017.
 */

public enum Option {

    // These BELOW are the types of sending mechanisms
    IR("Infrared", "Communicate with individual lights and sensors"),
    RF("Radio Frequency", "Communicate to many devices and set scenes"),

    // These BELOW are the basic options for IR
    INTENSITY("Intensity", "Alter light level of the light"),
    TYPE("DSI or DALI", "Choose the method of lighting control"),
    BURN("Burn In Time", "Off or 100hr"),
    MIN("Min Lamp", "Choose DSI percentage"),
    MOTION("Motion Line", "Choose motion version"),
    HOLD("Hold Overr", "Set to on or off"),
    START("10% Start", "Set to on or off"),
    POWER("Power Up", "Power on or power off"),
    BRIGHT("Bright-Out", "Set to on or off"),
    PIRSENS("PIR Sensitivity", "Change sensitivity of the sensor"),
    PIRENABLE("PIR Active", "Enale or disable the sensor"),
    VACANT("If Vacant", "Off / 30s-10h / cont"),
    SEC("Sec. Level", "Change the DSI level"),
    TDELAY("T-Delay", "30s-10h / cont"),


    // The BELOW are main OPTIONS.
    STATE("Power Options", "Turn on and off, view battery.."),
    SCENES("Scenes", "Create and change light scenes"),
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

    // These BELOW are all actions related to SCENES
    SCENESENABLE("Enable Scene Commands", "Switch to send commands via the RF TR PCB"),
    SCENE1("Scene 1", "Change light to scene 1"),
    SCENE2("Scene 2", "Change light to scene 2"),
    SCENE3("Scene 3", "Change light to scene 3"),
    SCENE4("Scene 4", "Change light to scene 4"),

    // These BELOW are actions related to address
    BUILDING("Change The Building Address", "This will set the address for the main building"),
    GROUP("Change The Group Address", "Give a device a group"),

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

