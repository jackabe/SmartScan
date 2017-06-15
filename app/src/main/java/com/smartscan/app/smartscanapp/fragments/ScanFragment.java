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

import com.smartscan.app.smartscanapp.DeviceCustomAdapter;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.model.DeviceItem;

import java.util.ArrayList;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanFragment extends Fragment {

    ArrayList<DeviceItem> scannedList;
    Button scanningButton;
    TextView scanningText;
    ListView scannedListView;
    BluetoothAdapter mBluetoothAdapter;
    private final static int REQUEST_ENABLE_BT = 1;
    DeviceItem item;
    DeviceCustomAdapter adapter;

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
                item = new DeviceItem();
                item.setDeviceName(device.getName());
                item.setDeviceCode(device.getAddress());
                scannedList.add(item);
                Log.i("BT", device.getName() + "\n" + device.getAddress());
            } else {
                Log.i("BT", "none" + "");
            }
            adapter = new DeviceCustomAdapter(
                    getActivity().getApplicationContext(), scannedList);
            scannedListView.setAdapter(adapter);
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        super.onActivityCreated(savedInstanceState);

        scannedList = new ArrayList<>();
        scanningButton = (Button) getActivity().findViewById(R.id.scanningButton);
        scanningText = (TextView) getActivity().findViewById(R.id.scanningText);
        scannedListView = (ListView) getActivity().findViewById(R.id.scannedListView);
        scannedListView.setVisibility(View.VISIBLE);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

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

        scanningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanningText.setText("Scanning...");
                mBluetoothAdapter.startDiscovery();
                mBluetoothAdapter.isDiscovering();

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
