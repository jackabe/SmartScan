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
public class TDelayFragment extends Fragment{

    private Fragment mFragment;

    private ListView actionListView;
    private Control control;
    private OptionAdapter adapter;
    private Option option;
    private ConnectThread connectThread;
    private TextView statusText;
    private ArrayList<Option>actions;

    private SendCommand command;

    public TDelayFragment() {
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
        actions = control.populateDelayActions();

        adapter = new OptionAdapter(
                getActivity().getApplicationContext(), actions);
        actionListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        command = new SendCommand(connectThread);

        actionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {

                option = actions.get(position);
                switch (option) {
                    case DELAY30S:
                        command.sendCommand(DataCommand.commandDelay30S);
                        break;
                    case DELAY1:
                        command.sendCommand(DataCommand.commandDelay1);
                        break;
                    case DELAY2:
                        command.sendCommand(DataCommand.commandDelay2);
                        break;
                    case DELAY3:
                        command.sendCommand(DataCommand.commandDelay3);
                        break;
                    case DELAY5:
                        command.sendCommand(DataCommand.commandDelay5);
                        break;
                    case DELAY10:
                        command.sendCommand(DataCommand.commandDelay10);
                        break;
                    case DELAY20:
                        command.sendCommand(DataCommand.commandDelay20);
                        break;
                    case DELAY30:
                        command.sendCommand(DataCommand.commandDelay30);
                        break;
                    case DELAY45:
                        command.sendCommand(DataCommand.commandDelay45);
                        break;
                    case DELAY1H:
                        command.sendCommand(DataCommand.commandDelay1H);
                        break;
                    case DELAY2H:
                        command.sendCommand(DataCommand.commandDelay2H);
                        break;
                    case DELAY3H:
                        command.sendCommand(DataCommand.commandDelay3H);
                        break;
                    case DELAY4H:
                        command.sendCommand(DataCommand.commandDelay4H);
                        break;
                    case DELAY5H:
                        command.sendCommand(DataCommand.commandDelay5H);
                        break;
                    case DELAY6H:
                        command.sendCommand(DataCommand.commandDelay6H);
                        break;
                    case DELAY7H:
                        command.sendCommand(DataCommand.commandDelay7H);
                        break;
                    case DELAY8H:
                        command.sendCommand(DataCommand.commandDelay8H);
                        break;
                    case DELAY9H:
                        command.sendCommand(DataCommand.commandDelay9H);
                        break;
                    case DELAY10H:
                        command.sendCommand(DataCommand.commandDelay10H);
                        break;
                    case DELAYCON:
                        command.sendCommand(DataCommand.commandDelayCont);
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
            fragmentManager.beginTransaction().replace(R.id.fragment_container, mFragment, "delay")
                    .addToBackStack("delay").commit();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}
