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
import android.widget.TextView;
import android.widget.Toast;

import com.smartscan.app.smartscanapp.ConnectThread;
import com.smartscan.app.smartscanapp.MainActivity;
import com.smartscan.app.smartscanapp.Adapters.OptionAdapter;
import com.smartscan.app.smartscanapp.Model.DataCommand;
import com.smartscan.app.smartscanapp.Model.SendCommand;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.Model.Menus.Control;
import com.smartscan.app.smartscanapp.Model.Menus.Option;
import com.smartscan.app.smartscanapp.Model.SendData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PIRMode extends Fragment{

    private Fragment mFragment;

    private ListView actionListView;
    private Control control;
    private OptionAdapter adapter;
    private Option option;
    private ConnectThread connectThread;
    private TextView statusText;
    private ArrayList<Option>actions;
    private SendCommand command;

    public PIRMode() {
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

        statusText = getActivity().findViewById(R.id.statusText);
        connectThread = ((MainActivity)getActivity()).getConnection();

        actionListView = (ListView) getActivity().findViewById(R.id.powerListView);
        control = new Control();
        actions = control.populateModeOptions();

        adapter = new OptionAdapter(
                getActivity().getApplicationContext(), actions);
        actionListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        command = new SendCommand(connectThread);

        actionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {

                option = actions.get(position);
                switch (option) {
                    case ENABLE:
                        command.sendCommand(DataCommand.commandEnable);
                        Toast enableToast = Toast.makeText(getActivity().getApplicationContext(), "Attempting Enable", Toast.LENGTH_SHORT);
                        enableToast.show();
                        break;
                    case DISABLE:
                        command.sendCommand(DataCommand.commandDisable);
                        Toast disableToast = Toast.makeText(getActivity().getApplicationContext(), "Attempting Disable", Toast.LENGTH_SHORT);
                        disableToast.show();
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
            fragmentManager.beginTransaction().replace(R.id.fragment_container, mFragment, "mode")
                    .addToBackStack("mode").commit();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}
