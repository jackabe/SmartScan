package com.smartscan.app.smartscanapp.fragments;

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
import com.smartscan.app.smartscanapp.MainActivity;
import com.smartscan.app.smartscanapp.OptionAdapter;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.model.Control;
import com.smartscan.app.smartscanapp.model.Option;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControlFragment extends Fragment {

    private Fragment mFragment;
    private ListView optionListView;
    private Control control;
    private OptionAdapter adapter;
    private Option option;
    ArrayList<Option>options;

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

        control = new Control();
        options = control.populateOptions();
        adapter = new OptionAdapter(
                getActivity().getApplicationContext(), options);
        optionListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        optionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {

                option = options.get(position);
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
