package com.smartscan.app.smartscanapp;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import static android.provider.ContactsContract.Intents.Insert.NAME;

public class MainActivity extends AppCompatActivity {

    private TextView scanningText;
    private ListView pairedListView;
    private ListView scannedListView;
    private Button scanningButton;
    private final static int REQUEST_ENABLE_BT = 1;
    private ArrayList<String> pairedList;
    private ArrayList<String> scannedList;
    private BluetoothAdapter mBluetoothAdapter;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pairedList = new ArrayList<>();
        scannedList = new ArrayList<>();
        scanningButton = (Button) findViewById(R.id.scanningButton);
        scanningText = (TextView) findViewById(R.id.scanningText);
        pairedListView = (ListView) findViewById(R.id.pairedListView);
        scannedListView = (ListView) findViewById(R.id.scannedListView);
        scannedListView.setVisibility(View.GONE);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        if (Build.VERSION.SDK_INT >= 15 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        if (mBluetoothAdapter == null) {
            scanningText.setText("Your device does not support Bluetooth, Sorry!");
        } else if (!mBluetoothAdapter.isEnabled()) {
            scanningText.setText("You need to enable bluetooth to use this app..");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                pairedList.add(device.getName() + "\n" + device.getAddress());
            }
            Log.i("found", pairedDevices + "");
            pairedListView.setAdapter(new ArrayAdapter<>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, pairedList));
        }

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pairedListView.setVisibility(View.GONE);
                    scannedListView.setVisibility(View.VISIBLE);
                } else {
                    pairedListView.setVisibility(View.VISIBLE);
                    scannedListView.setVisibility(View.GONE);
                }
            }
        });

        scanningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanningText.setText("Scanning...");
                mBluetoothAdapter.startDiscovery();
                mBluetoothAdapter.isDiscovering();
                Toast toast = Toast.makeText(getApplicationContext(), "Found Devices!", Toast.LENGTH_LONG);
                toast.show();
                scanningText.setText("Click on a device to connect");
            }
        });

        scannedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = scannedListView.getItemAtPosition(position).toString();

                Toast toast = Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.i("found", "hello" + "");
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                scannedList.add(device.getName() + "\n" + device.getAddress());
                Log.i("BT", device.getName() + "\n" + device.getAddress());
                scannedListView.setAdapter(new ArrayAdapter<>(context,
                        android.R.layout.simple_list_item_1, scannedList));
            } else {
                Log.i("BT", "none" + "");
            }
        }
    };
}
