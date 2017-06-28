package com.smartscan.app.smartscanapp.Fragments.IR.Basic;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.smartscan.app.smartscanapp.ConnectThread;
import com.smartscan.app.smartscanapp.MainActivity;
import com.smartscan.app.smartscanapp.Adapters.OptionAdapter;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.Model.Menus.Control;
import com.smartscan.app.smartscanapp.Model.Menus.Option;
import com.smartscan.app.smartscanapp.Model.SendData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends Fragment {

    private Fragment mFragment;

    private ListView actionListView;
    private Control control;
    private OptionAdapter adapter;
    private Option option;
    private ConnectThread connectThread;
    private ArrayList<Option> actions;
    private SendData dataSender;
    private EditText valueInput;
    private AlertDialog.Builder addressBuilder;
    private String inputValue;
    private String start, end;

    public AddressFragment() {
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

        connectThread = ((MainActivity) getActivity()).getConnection();

        actionListView = (ListView) getActivity().findViewById(R.id.powerListView);
        control = new Control();
        actions = control.populateAddressActions();

        adapter = new OptionAdapter(
                getActivity().getApplicationContext(), actions);
        actionListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        actionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {

                option = actions.get(position);
                addressBuilder = new AlertDialog.Builder(getActivity());
                addressBuilder.setMessage("Set Address");
                addressBuilder.setTitle("Enter a value between 1-254");

                valueInput = new EditText(getActivity());
                valueInput.setPadding(50, 50, 50, 50);
                addressBuilder.setView(valueInput);

                addressBuilder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputValue = valueInput.getText().toString();
                        start = "I1";
                        switch (option) {
                            case BUILDING:
                                dataSender = new SendData(connectThread);
                                dataSender.sendData(start, "10" +inputValue);
                                Toast.makeText(getActivity(), "Building set to " + inputValue, Toast.LENGTH_SHORT).show();
                                break;
                            case GROUP:
                                dataSender = new SendData(connectThread);
                                dataSender.sendData(start, "11" +inputValue);
                                Toast.makeText(getActivity(), "Group set to " + inputValue, Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });

                addressBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                addressBuilder.show();
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
