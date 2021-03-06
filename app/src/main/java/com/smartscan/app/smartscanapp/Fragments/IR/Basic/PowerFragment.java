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
import android.widget.Toast;

import com.smartscan.app.smartscanapp.ConnectThread;
import com.smartscan.app.smartscanapp.MainActivity;
import com.smartscan.app.smartscanapp.Adapters.OptionAdapter;
import com.smartscan.app.smartscanapp.Model.DataCommand;
import com.smartscan.app.smartscanapp.Model.SendCommand;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.Model.Menus.Control;
import com.smartscan.app.smartscanapp.Model.Menus.Option;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PowerFragment extends Fragment{

    private Fragment mFragment;

    private ListView actionListView;
    private Control control;
    private OptionAdapter adapter;
    private Option option;
    private ConnectThread connectThread;
    private ArrayList<Option>actions;
    private SendCommand command;

    public PowerFragment() {
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
        actions = control.populatePowerOptions();

        adapter = new OptionAdapter(
                getActivity().getApplicationContext(), actions);
        actionListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        command = new SendCommand(connectThread);

        actionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {

                option = actions.get(position);
                switch (option) {
                    case TURNON:
                        command.sendCommand(DataCommand.commandOn);
                        Toast onToast = Toast.makeText(getActivity().getApplicationContext(), "Attempting To Turn On", Toast.LENGTH_SHORT);
                        onToast.show();
                        break;
                    case TURNOFF:
                        command.sendCommand(DataCommand.commandOff);
                        Toast offToast = Toast.makeText(getActivity().getApplicationContext(), "Attempting To Turn Off", Toast.LENGTH_SHORT);
                        offToast.show();
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
            fragmentManager.beginTransaction().replace(R.id.fragment_container, mFragment, "power")
                    .addToBackStack("power").commit();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}
