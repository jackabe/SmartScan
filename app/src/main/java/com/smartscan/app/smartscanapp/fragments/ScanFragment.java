package com.smartscan.app.smartscanapp.fragments;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.smartscan.app.smartscanapp.ConnectThread;
import com.smartscan.app.smartscanapp.DeviceCustomAdapter;
import com.smartscan.app.smartscanapp.MainActivity;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.model.DeviceItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanFragment extends Fragment {

    ArrayList<DeviceItem> scannedList;
    Button scanningButton;
    TextView scanningText;
    ListView scannedListView;
    BluetoothAdapter mBluetoothAdapter;
    DeviceItem item;
    DeviceCustomAdapter adapter;
    BluetoothDevice device;
    ArrayList<BluetoothDevice> devices;
    String name;
    private Fragment mFragment;

    private static final int REQUEST_ENABLE_BT = 3;

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
        devices = new ArrayList<>();


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

        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(mReceiver, filter);

        scannedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                device = devices.get(position);
                item = scannedList.get(position);
                name = scannedList.get(position).getDeviceName();
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Connecting to " + item.getDeviceName() + "" + name, Toast.LENGTH_LONG);
                toast.show();
                mBluetoothAdapter.cancelDiscovery();

                ((MainActivity)getActivity()).setDevice(device);
                mFragment = new ControlFragment();
                attachFragment();
            }
        });
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devices.add(device);
                item = new DeviceItem();
                item.setDeviceName(device.getName());
                item.setDeviceCode(device.getAddress());
                scannedList.add(item);
            } else {
                Log.i("BT", "none" + "");
            }
            adapter = new DeviceCustomAdapter(
                    getActivity().getApplicationContext(), scannedList);
            scannedListView.setAdapter(adapter);
        }
    };

    private void attachFragment() {
        if (mFragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, mFragment).commit();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

}
