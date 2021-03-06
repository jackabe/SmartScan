package com.smartscan.app.smartscanapp.Fragments;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smartscan.app.smartscanapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment{

    private Fragment mFragment;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        super.onActivityCreated(savedInstanceState);

        // Inflate the layout for this fragment
        Button scanButton = (Button) getActivity().findViewById(R.id.scanningButton);
        Button deviceButton = (Button) getActivity().findViewById(R.id.deviceButton);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment = new ScanFragment();
                attachFragment();
            }
        });

        deviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment = new DeviceFragment();
                attachFragment();
            }
        });
    }

    // Our custom method to attach/replace Fragments
    private void attachFragment() {
        if (mFragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, mFragment, "main")
                    .addToBackStack("main").commit();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}
