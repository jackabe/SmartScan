package com.smartscan.app.smartscanapp.fragments;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
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

import com.smartscan.app.smartscanapp.ConnectThread;
import com.smartscan.app.smartscanapp.DeviceCustomAdapter;
import com.smartscan.app.smartscanapp.DrawerItemCustomAdapter;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.model.DeviceItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment{

    ArrayList<DeviceItem> pairedList;
    ListView pairedListView;
    BluetoothAdapter mBluetoothAdapter;
    private final static int REQUEST_ENABLE_BT = 1;
    DeviceItem item;
    DeviceCustomAdapter adapter;
    BluetoothDevice device;
    ArrayList<BluetoothDevice> devices;
    String name;

    private Fragment mFragment;
    ConnectThread connectThread;

    public DeviceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_device, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        super.onActivityCreated(savedInstanceState);

        pairedList = new ArrayList<>();
        pairedListView = (ListView) getActivity().findViewById(R.id.pairedListView);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        devices = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= 15 &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        if (mBluetoothAdapter == null) {

        } else if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                item = new DeviceItem();
                item.setDeviceName(deviceName);
//                device.getName() + "\n" + device.getAddress());
                pairedList.add(item);
                devices.add(device);
            }
            Log.i("found", pairedDevices + "");
            adapter = new DeviceCustomAdapter(
                    getActivity().getApplicationContext(), pairedList);
            pairedListView.setAdapter(adapter);

        }

        pairedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
                device = devices.get(position);
                item = pairedList.get(position);
                name = pairedList.get(position).getDeviceName();
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Connecting to " + item.getDeviceName() + "" + name, Toast.LENGTH_LONG);
                toast.show();
                mBluetoothAdapter.cancelDiscovery();

                Bundle theDevice = new Bundle();
                theDevice.putParcelable("Device", device);
                mFragment = new ControlFragment();
                mFragment.setArguments(theDevice);
                attachFragment();
            }
        });
    }

    private void attachFragment() {
        if (mFragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, mFragment).commit();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}



