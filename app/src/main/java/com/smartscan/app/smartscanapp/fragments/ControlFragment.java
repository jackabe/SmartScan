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
public class ControlFragment extends Fragment {

    private Fragment mFragment;
    ConnectThread connectThread;
    BluetoothDevice device;
    BluetoothAdapter mBluetoothAdapter;
    List<UUID> uuidCandidates;
    BluetoothDevice deviceToBeSent;
    TextView statusText;
    String message;
    MessageInterface messageInterface;
    private ListView optionListView;
    private Control control;
    private OptionAdapter adapter;
    private Option option;
    StatusTextFragment fragment;

    public ControlFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_control, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        super.onActivityCreated(savedInstanceState);

        optionListView = (ListView) getActivity().findViewById(R.id.optionListView);

        optionListView = (ListView) getActivity().findViewById(R.id.optionListView);

        control = new Control();
        adapter = new OptionAdapter(
                getActivity().getApplicationContext(), control.populateOptions());
        optionListView.setAdapter(adapter);

        optionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {

                option = control.populateOptions().get(position);
                switch (option) {
                    case STATE:
                        mFragment = new PowerFragment();
                        attachFragment();
                        break;
                    default:
                        break;
                }
            }
        });

        ((MainActivity)getActivity()).connectToDevice();
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
}
