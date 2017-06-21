package com.smartscan.app.smartscanapp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import com.smartscan.app.smartscanapp.fragments.ControlFragment;
import com.smartscan.app.smartscanapp.fragments.MainFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

public class ConnectThread {

    private BluetoothSocketWrapper bluetoothSocket;
    private BluetoothDevice device;
    private boolean secure;
    private BluetoothAdapter adapter;
    private List<UUID> uuidCandidates;
    private int candidate;

    /**
     * @param device the device
     * @param secure if connection should be done via a secure socket
     * @param adapter the Android BT adapter
     * @param uuidCandidates a list of UUIDs. if null or empty, the Serial PP id is used
     */
    public ConnectThread(BluetoothDevice device, boolean secure, BluetoothAdapter adapter,
                              List<UUID> uuidCandidates) {
        this.device = device;
        this.secure = secure;
        this.adapter = adapter;
        this.uuidCandidates = uuidCandidates;

        if (this.uuidCandidates == null || this.uuidCandidates.isEmpty()) {
            this.uuidCandidates = new ArrayList<UUID>();
            this.uuidCandidates.add(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        }
    }

    public BluetoothSocketWrapper connect() throws IOException {
        boolean success = false;
        while (selectSocket()) {
            adapter.cancelDiscovery();
            try {
                bluetoothSocket.connect();
                success = true;
                break;
            } catch (IOException e) {
                //try the fallback
                try {
                    bluetoothSocket = new FallbackBluetoothSocket(bluetoothSocket.getUnderlyingSocket());
                    Thread.sleep(500);
                    bluetoothSocket.connect();
                    success = true;
                    break;
                } catch (FallbackException e1) {
                    Log.w("BT", "Could not initialize FallbackBluetoothSocket classes.", e);
                } catch (InterruptedException e1) {
                    Log.w("BT", e1.getMessage(), e1);
                } catch (IOException e1) {
                    Log.w("BT", "Fallback failed. Cancelling.", e1);
                }
            }
        }

        if (!success) {
            throw new IOException("Could not connect to device: "+ device.getAddress());
        }

        receiveData(bluetoothSocket);
        return bluetoothSocket;
    }

    private boolean selectSocket() throws IOException {
        if (candidate >= uuidCandidates.size()) {
            return false;
        }

        BluetoothSocket tmp;
        UUID uuid = uuidCandidates.get(candidate++);

        Log.i("BT", "Attempting to connect to Protocol: "+ uuid);
        if (secure) {
            tmp = device.createRfcommSocketToServiceRecord(uuid);
        } else {
            tmp = device.createInsecureRfcommSocketToServiceRecord(uuid);
        }
        bluetoothSocket = new NativeBluetoothSocket(tmp);

        return true;
    }

    public static interface BluetoothSocketWrapper {

        InputStream getInputStream() throws IOException;

        OutputStream getOutputStream() throws IOException;

        String getRemoteDeviceName();

        void connect() throws IOException;

        String getRemoteDeviceAddress();

        void close() throws IOException;

        BluetoothSocket getUnderlyingSocket();

    }

    public static class NativeBluetoothSocket implements BluetoothSocketWrapper {

        private BluetoothSocket socket;

        public NativeBluetoothSocket(BluetoothSocket tmp) {
            this.socket = tmp;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return socket.getInputStream();
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            return socket.getOutputStream();
        }

        @Override
        public String getRemoteDeviceName() {
            return socket.getRemoteDevice().getName();
        }

        @Override
        public void connect() throws IOException {
            socket.connect();
        }

        @Override
        public String getRemoteDeviceAddress() {
            return socket.getRemoteDevice().getAddress();
        }

        @Override
        public void close() throws IOException {
            socket.close();
        }

        @Override
        public BluetoothSocket getUnderlyingSocket() {
            return socket;
        }

    }

    public class FallbackBluetoothSocket extends NativeBluetoothSocket {

        private BluetoothSocket fallbackSocket;

        public FallbackBluetoothSocket(BluetoothSocket tmp) throws FallbackException {
            super(tmp);
            try
            {
                Class<?> clazz = tmp.getRemoteDevice().getClass();
                Class<?>[] paramTypes = new Class<?>[] {Integer.TYPE};
                Method m = clazz.getMethod("createRfcommSocket", paramTypes);
                Object[] params = new Object[] {Integer.valueOf(1)};
                fallbackSocket = (BluetoothSocket) m.invoke(tmp.getRemoteDevice(), params);
            }
            catch (Exception e)
            {
                throw new FallbackException(e);
            }
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return fallbackSocket.getInputStream();
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            return fallbackSocket.getOutputStream();
        }


        @Override
        public void connect() throws IOException {
            fallbackSocket.connect();
        }


        @Override
        public void close() throws IOException {
            fallbackSocket.close();
        }

    }

    public static class FallbackException extends Exception {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public FallbackException(Exception e) {
            super(e);
        }

    }

    public void sendData(JSONObject object) throws IOException{
        //JSONArray jsonArray = new JSONArray();
        //jsonArray.put(x);
        //jsonArray.put(y);
        String jsonStr = object.toString();
        OutputStream socketOutputStream =  bluetoothSocket.getOutputStream();
        socketOutputStream.write(jsonStr.toString().getBytes("utf-8"));
        Log.i("sending", jsonStr);
    }

    public boolean receiveData(BluetoothSocketWrapper socket) throws IOException{
        InputStream socketInputStream =  socket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytes;
        int numBytes;

        // Keep looping to listen for received messages
        while (true) {
            try {
                bytes = socketInputStream.read(buffer);            //read bytes from input buffer
                String readMessage = new String(buffer, 0, bytes);
                Log.i("logging", readMessage);

                if (readMessage.contains("Received 86")) {
                    if (readMessage.contains("Received 86 C0")) {
                        Log.i("check", "Okay");
                        EventBus.getDefault().post(new com.smartscan.app.smartscanapp.model.Message("Success! - Turned On"));
                    }
                    else if (readMessage.contains("Received 86 00")) {
                        Log.i("check", "Okay");
                        EventBus.getDefault().post(new com.smartscan.app.smartscanapp.model.Message("Success! - Turned Off"));
                    }
                    else {
                        EventBus.getDefault().post(new com.smartscan.app.smartscanapp.model.Message("Failed!"));
                    }
                }

                else {

                }
            } catch (IOException e) {
                break;
            }
        }
        return false;
    }

    public void close() throws IOException {
        bluetoothSocket.close();
    }

}