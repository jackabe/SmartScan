package com.smartscan.app.smartscanapp.model;

/**
 * Created by Jack_Allcock on 20/06/2017.
 */

public enum Option {

    STATE("Power Options", "Turn on and off, view battery.."),
    SCENES("Scenes", "Create and change light scenes"),
    MOTION("Motion", "Adjust the light motion settings"),
    INTENSITY("Intensity", "Alter intensity of the light"),
    STATUS("Status", "Monitor light status"),
    ADDRESSES("Addresses", "Change light addresses, E.g. group, building..");

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

    public String getName(String optionName) {
        try {
            for (Option option : Option.values()) {
                if (option.optionName.equals(optionName)) {
                    return option.getOptionName();
                }
            }
            throw new IllegalArgumentException("Couldn't get option");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught an error..." + e.getMessage());
        }
        return "";
    }

    public String getDescription(String optionName) {
        try {
            for (Option option : Option.values()) {
                if (option.optionName.equals(optionName)) {
                    return option.getOptionDesc();
                }
            }
            throw new IllegalArgumentException("Couldn't get option");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught an error..." + e.getMessage());
        }
        return "";
    }

}

