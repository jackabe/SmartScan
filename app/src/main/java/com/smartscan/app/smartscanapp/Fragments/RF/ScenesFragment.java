package com.smartscan.app.smartscanapp.Fragments.RF;

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
import android.widget.Toast;

import com.smartscan.app.smartscanapp.ConnectThread;
import com.smartscan.app.smartscanapp.MainActivity;
import com.smartscan.app.smartscanapp.Adapters.OptionAdapter;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.Model.Menus.Control;
import com.smartscan.app.smartscanapp.Model.Menus.Option;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScenesFragment extends Fragment {

    private Fragment mFragment;

    private ListView actionListView;
    private Control control;
    private OptionAdapter adapter;
    private Option option;
    private ConnectThread connectThread;
    private ArrayList<Option>actions;

    public ScenesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_power, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        super.onActivityCreated(savedInstanceState);

        connectThread = ((MainActivity)getActivity()).getConnection();

        actionListView = (ListView) getActivity().findViewById(R.id.powerListView);
        control = new Control();
        actions = control.populateSceneActions();

        adapter = new OptionAdapter(
                getActivity().getApplicationContext(), actions);
        actionListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        actionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {

                option = actions.get(position);
                switch (option) {
                    case SCENESENABLE:
                        Toast.makeText(getActivity(), "Enabling.", Toast.LENGTH_SHORT).show();
                        try {
                            connectThread.sendRadio("S");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    case SCENE2:
                        Toast.makeText(getActivity(), "Setting Scene 2", Toast.LENGTH_SHORT).show();
                        try {
                            connectThread.sendRadio("10 11 90 03 00 5D F1");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case SCENE3:
                        Toast.makeText(getActivity(), "Setting Scene 3", Toast.LENGTH_SHORT).show();
                        try {
                            connectThread.sendRadio("10 11 60 03 00 5D F1");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case SCENE4:
                        Toast.makeText(getActivity(), "Setting Scene 4", Toast.LENGTH_SHORT).show();
                        try {
                            connectThread.sendRadio("10 11 04 03 00 5D F1");
                        } catch (IOException e) {
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
}
