package com.smartscan.app.smartscanapp.Model;

import de.greenrobot.event.EventBus;

/**
 * Created by Jack_Allcock on 22/06/2017.
 */

public class DeviceMessageListener {

    public void checkMessages(String message) {

        // The following class is a receiver and sender of messages.
        // It is checking the messages being sent to the Connect Thread from the sensor.
        // It then sends a new message to the status bar fragment if the message received is correct.

        // Add new receiver if statements here when you want to listen to new known messages

        // Receivers for Power Option Fragment:


        //Receiver for enable and disable
        if (message.contains("Received 86")) {
            if (message.contains("Received 86 C0")) {
                EventBus.getDefault().post(new MessageSystem("Success! - Enabled"));
            }
            else if (message.contains("Received 86 00")) {
                EventBus.getDefault().post(new MessageSystem("Success! - Disabled"));
            }
            else {
                EventBus.getDefault().post(new MessageSystem("Failed!"));
            }
        }

        //Receiver for enable and disable
        else if (message.contains("Received 8B")) {
            if (message.contains("Received 8B FF")) {
                EventBus.getDefault().post(new MessageSystem("Success! - Turned On"));
            }
            else if (message.contains("Received 8B 00")) {
                EventBus.getDefault().post(new MessageSystem("Success! - Turned Off"));
            }
            else {
                EventBus.getDefault().post(new MessageSystem("Failed!"));
            }
        }


        // Receivers for light intensity fragment:


        else {

        }
    }

}
