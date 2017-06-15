package com.smartscan.app.smartscanapp.fragments;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

import android.Manifest;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.smartscan.app.smartscanapp.R;

import java.util.ArrayList;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanFragment extends Fragment {

    ArrayList pairedList;
    ArrayList scannedList;
    Button scanningButton;
    TextView scanningText;
    ListView pairedListView;
    ListView scannedListView;
    BluetoothAdapter mBluetoothAdapter;
    ToggleButton toggleButton;
    private final static int REQUEST_ENABLE_BT = 1;

    public ScanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_scan, container, false);
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mReceiver);
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        super.onActivityCreated(savedInstanceState);

        pairedList = new ArrayList<>();
        scannedList = new ArrayList<>();
        scanningButton = (Button) getActivity().findViewById(R.id.scanningButton);
        scanningText = (TextView) getActivity().findViewById(R.id.scanningText);
        pairedListView = (ListView) getActivity().findViewById(R.id.pairedListView);
        scannedListView = (ListView) getActivity().findViewById(R.id.scannedListView);
        scannedListView.setVisibility(View.GONE);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        toggleButton = (ToggleButton) getActivity().findViewById(R.id.toggleButton);

        if (Build.VERSION.SDK_INT >= 15 &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
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
            pairedListView.setAdapter(new ArrayAdapter<>(getActivity(),
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
                Toast toast = Toast.makeText(getActivity(), "Found Devices!", Toast.LENGTH_LONG);
                toast.show();
                scanningText.setText("Click on a device to connect");
            }
        });

        scannedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = scannedListView.getItemAtPosition(position).toString();

                Toast toast = Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(mReceiver, filter);
    }

}
