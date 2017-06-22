package com.smartscan.app.smartscanapp.fragments;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.smartscan.app.smartscanapp.Adapters.TemplateOptionAdapter;
import com.smartscan.app.smartscanapp.Adapters.TemplatesViewAdapter;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.model.Control;
import com.smartscan.app.smartscanapp.model.Option;
import com.smartscan.app.smartscanapp.model.Template;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewTemplate extends Fragment {

    private Fragment mFragment;
    private Template template;
    private Bundle bundle;
    private ListView templateOptionsView;
    private Control control;
    private Option option;
    private ArrayList<Option>templateOptions;
    private TemplateOptionAdapter adapter;

    public ViewTemplate() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_template_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        super.onActivityCreated(savedInstanceState);

        control = new Control();
        templateOptions = control.populateTemplateActions();
        templateOptionsView = getActivity().findViewById(R.id.templateOptionsView);
        bundle = new Bundle();
        template = (Template) bundle.getParcelable("Template");

        populateListView();

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

    public void populateListView() {
        adapter = new TemplateOptionAdapter(
                getActivity(), templateOptions, template);
        templateOptionsView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}
