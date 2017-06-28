package com.smartscan.app.smartscanapp.Fragments.IR.Basic;

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
import android.widget.AdapterView;
import android.widget.ListView;

import com.smartscan.app.smartscanapp.Adapters.BasicIRSettings;
import com.smartscan.app.smartscanapp.ConnectThread;
import com.smartscan.app.smartscanapp.Fragments.RF.ScenesFragment;
import com.smartscan.app.smartscanapp.MainActivity;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.Model.Menus.Control;
import com.smartscan.app.smartscanapp.Model.Menus.Option;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicFragment extends Fragment {

    private Fragment mFragment;
    private ListView optionListView;
    private Control control;
    private BasicIRSettings adapter;
    private Option option;
    ArrayList<Option>options;
    private ConnectThread connectThread;

    public BasicFragment() {
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

        connectThread = ((MainActivity)getActivity()).getConnection();

        optionListView = (ListView) getActivity().findViewById(R.id.optionListView);
        control = new Control();
        options = control.populateBasicIR();

        adapter = new BasicIRSettings(
                getActivity().getApplicationContext(), options, connectThread);
        optionListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        optionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {

                option = options.get(position);
                switch (option) {
                    case POWER:
                        mFragment = new PowerFragment();
                        attachFragment();
                        break;
                    case PIRMODE:
                        mFragment = new PIRMode();
                        attachFragment();
                        break;
                    case PIRSENS:
                        mFragment = new PIRSensitivity();
                        attachFragment();
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
            fragmentManager.beginTransaction().replace(R.id.fragment_container, mFragment, "basic")
                    .addToBackStack("basic").commit();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}
