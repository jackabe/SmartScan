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
import com.smartscan.app.smartscanapp.model.Option;

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

    private ListView actionListView;
    private Control control;
    private OptionAdapter adapter;
    private Option option;
    private ConnectThread connectThread;
    private TextView statusText;
    private ArrayList<Option>actions;

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

        statusText = getActivity().findViewById(R.id.statusText);
        connectThread = ((MainActivity)getActivity()).getConnection();

        actionListView = (ListView) getActivity().findViewById(R.id.powerListView);
        control = new Control();
        actions = control.populateActions();

        adapter = new OptionAdapter(
                getActivity().getApplicationContext(), actions);
        actionListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        actionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {

                option = actions.get(position);
                switch (option) {
                    case TURNON:
                        Toast onToast = Toast.makeText(getActivity().getApplicationContext(), "Attempting Turn On", Toast.LENGTH_SHORT);
                        onToast.show();
                        JSONObject turnOn = new JSONObject();
                        try {
                            turnOn.put("I1", "06C0");
                            try {
                                connectThread.sendData(turnOn);
                                statusText.setTextColor(Color.GREEN);
                            } catch (IOException e) {

                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                    case TURNOFF:
                        Toast toastOff = Toast.makeText(getActivity().getApplicationContext(), "Attempting Turn Off", Toast.LENGTH_SHORT);
                        toastOff.show();
                        JSONObject turnOff = new JSONObject();
                        try {
                            turnOff.put("I1", "0600");
                            try {
                                connectThread.sendData(turnOff);
                                statusText.setTextColor(Color.GREEN);
                            } catch (IOException e) {

                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
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
