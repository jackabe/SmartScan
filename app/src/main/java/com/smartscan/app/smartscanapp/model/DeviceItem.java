package com.smartscan.app.smartscanapp.Model;

import java.util.UUID;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

public class DeviceItem {

    private String deviceName;
    private String deviceCode;
    private UUID deviceId;

    public DeviceItem() {

    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
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
