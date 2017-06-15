package com.smartscan.app.smartscanapp.model;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

public class DeviceItem {

    private String deviceName;
    private String deviceCode;

    public DeviceItem() {

    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
