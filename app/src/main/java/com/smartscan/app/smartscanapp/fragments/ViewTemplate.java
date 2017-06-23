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
import android.widget.ListView;
import android.widget.Toast;

import com.smartscan.app.smartscanapp.Adapters.TemplateOptionAdapter;
import com.smartscan.app.smartscanapp.ConnectThread;
import com.smartscan.app.smartscanapp.Database.DBConnector;
import com.smartscan.app.smartscanapp.MainActivity;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.Model.Control;
import com.smartscan.app.smartscanapp.Model.Option;
import com.smartscan.app.smartscanapp.Model.SendData;
import com.smartscan.app.smartscanapp.Model.Template;

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
    private Button saveButton;
    private Button applyButton;
    private DBConnector dbConnector;
    private ConnectThread connectThread;
    private int power;
    private int enable;
    private SendData dataSender;

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

        dbConnector = new DBConnector(getActivity());
        saveButton = getActivity().findViewById(R.id.templateSaveButton);
        applyButton = getActivity().findViewById(R.id.templateApplyButton);
        control = new Control();
        templateOptions = control.populateTemplateActions();
        templateOptionsView = getActivity().findViewById(R.id.templateOptionsView);
        bundle = new Bundle();
        template = ((MainActivity)getActivity()).getTemplate();

        populateListView();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Template updateTemplate = getTemplate();
                String name = updateTemplate.getTemplateName();
                power = updateTemplate.getTemplatePower();
                enable = updateTemplate.getTemplateStatus();
                dbConnector.updateQuery(name, power, enable);
                Toast.makeText(getActivity(), "Template Saved", Toast.LENGTH_LONG).show();
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(((MainActivity)getActivity()).isConnected()) {
                     connectThread = (((MainActivity) getActivity()).getConnection());
                     dataSender = new SendData(connectThread);
                     if (power == 1) {
                         dataSender.sendData("I1", "0BFF");
                     }
                     else if (power == 0){
                         dataSender.sendData("I1", "0B00");
                     }
                     else {
                         Toast.makeText(getActivity(), "Error sending command", Toast.LENGTH_SHORT).show();
                     }

                     if (enable == 1) {
                         dataSender.sendData("I1", "06C0");
                     }
                     else if (enable == 0){
                         dataSender.sendData("I1", "0600");
                     }
                     else {
                         Toast.makeText(getActivity(), "Error sending command", Toast.LENGTH_SHORT).show();
                     }
                 }
                else {
                     Toast.makeText(getActivity(), "Please connect to a device first!", Toast.LENGTH_LONG).show();
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

    public void populateListView() {
        adapter = new TemplateOptionAdapter(
                getActivity(), templateOptions, template, ViewTemplate.this);
        templateOptionsView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Template getTemplate() {
        return template;
    }
}
