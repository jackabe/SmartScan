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
public class PIRSensitivity extends Fragment{

    private Fragment mFragment;

    private ListView actionListView;
    private Control control;
    private OptionAdapter adapter;
    private Option option;
    private ConnectThread connectThread;
    private TextView statusText;
    private ArrayList<Option>actions;

    private SendCommand command;

    public PIRSensitivity() {
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
        actions = control.populateSensitivityOptions();

        adapter = new OptionAdapter(
                getActivity().getApplicationContext(), actions);
        actionListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        command = new SendCommand(connectThread);

        actionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {

                option = actions.get(position);
                switch (option) {
                    case SENSMAX:
                        command.sendCommand(DataCommand.commandSensitivityMax);
                        Toast.makeText(getActivity().getApplicationContext(), "Setting sensitivity to Max", Toast.LENGTH_SHORT).show();
                        break;
                    case SENS5:
                        command.sendCommand(DataCommand.commandSensitivity5);
                        Toast.makeText(getActivity().getApplicationContext(), "Setting sensitivity to 5", Toast.LENGTH_SHORT).show();
                        break;
                    case SENS4:
                        command.sendCommand(DataCommand.commandSensitivity4);
                        Toast.makeText(getActivity().getApplicationContext(), "Setting sensitivity to 4", Toast.LENGTH_SHORT).show();
                        break;
                    case SENS3:
                        command.sendCommand(DataCommand.commandSensitivity3);
                        Toast.makeText(getActivity().getApplicationContext(), "Setting sensitivity to 3", Toast.LENGTH_SHORT).show();
                        break;
                    case SENS2:
                        command.sendCommand(DataCommand.commandSensitivity2);
                        Toast.makeText(getActivity().getApplicationContext(), "Setting sensitivity to 2", Toast.LENGTH_SHORT).show();
                        break;
                    case SENS1:
                        command.sendCommand(DataCommand.commandSensitivity1);
                        Toast.makeText(getActivity().getApplicationContext(), "Setting sensitivity to 1", Toast.LENGTH_SHORT).show();
                        break;
                    case SENSMIN:
                        command.sendCommand(DataCommand.commandSensitivityMin);
                        Toast.makeText(getActivity().getApplicationContext(), "Setting sensitivity to Min", Toast.LENGTH_SHORT).show();
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
            fragmentManager.beginTransaction().replace(R.id.fragment_container, mFragment, "sensitivity")
                    .addToBackStack("sensitivity").commit();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}
