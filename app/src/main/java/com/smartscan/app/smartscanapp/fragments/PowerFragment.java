package com.smartscan.app.smartscanapp.fragments;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.smartscan.app.smartscanapp.MessageInterface;
import com.smartscan.app.smartscanapp.OptionAdapter;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.model.Control;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class PowerFragment extends Fragment implements MainActivity.OnBackPressedListener{

    private Fragment mFragment;
    private ConnectThread connectThread;
    private Button offButton;
    private Button onButton;
    private TextView statusText;

    public PowerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setOnBackPressedListener(this);
        return inflater.inflate(R.layout.fragment_power, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        super.onActivityCreated(savedInstanceState);

        connectThread = ((MainActivity)getActivity()).getConnection();
        onButton = (Button)getActivity().findViewById(R.id.turnOn);
        offButton = (Button)getActivity().findViewById(R.id.turnOff);
        statusText = (TextView)getActivity().findViewById(R.id.statusText);

        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "turning off", Toast.LENGTH_SHORT);
                toast.show();
                JSONObject turnOff = new JSONObject();
                try {
                    turnOff.put("I1", "0600");
                    try {
                        connectThread.sendData(turnOff);
                        statusText.setText("Attempting Turn Off...");
                        statusText.setTextColor(Color.YELLOW);
                    } catch (IOException e) {

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "turning on", Toast.LENGTH_SHORT);
                toast.show();
                JSONObject turnOn = new JSONObject();
                try {
                    turnOn.put("I1", "06C0");
                    try {
                        connectThread.sendData(turnOn);
                        statusText.setText("Attempting Turn On...");
                        statusText.setTextColor(Color.YELLOW);
                    } catch (IOException e) {

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    // Our custom method to attach/replace Fragments
    private void attachFragment() {
        if (mFragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, mFragment).commit();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void doBack() {
        mFragment = new ControlFragment();
        attachFragment();
    }
}
