package com.smartscan.app.smartscanapp.Fragments;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.smartscan.app.smartscanapp.Adapters.TemplatesViewAdapter;
import com.smartscan.app.smartscanapp.Database.DBConnector;
import com.smartscan.app.smartscanapp.MainActivity;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.Model.Template;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemplateHome extends Fragment {

    private Fragment mFragment;
    private ListView templatesListView;
    private TemplatesViewAdapter adapter;
    private ArrayList<Template>templates;
    private Template template;
    private DBConnector db;
    private FloatingActionButton addNewTemplate;
    private String templateName;
    private String templateDescription;
    private Random r;

    public TemplateHome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_template_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        super.onActivityCreated(savedInstanceState);

        r = new Random();
        addNewTemplate = getActivity().findViewById(R.id.addNewTemplate);
        templatesListView = (ListView) getActivity().findViewById(R.id.templatesListView);
        templates = new ArrayList<>();
        db = new DBConnector(getActivity().getApplicationContext());

        populateListView();

        addNewTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setMessage("Enter Name");
                alert.setTitle("Add A new Template");

                final EditText name = new EditText(getActivity());
                alert.setView(name);

                alert.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        templateName = name.getText().toString();
                        if (templateName.equals("") || templateName.equals(" ")  ) {
                            int tempName = r.nextInt(10000 - 1) + 1;
                            templateName = "Template" + "" + String.valueOf(tempName);
                            Toast temp = Toast.makeText(getActivity(), templateName, Toast.LENGTH_LONG);
                            temp.show();
                        }

                        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        final EditText description = new EditText(getActivity());
                        alert.setMessage("Enter Description");
                        alert.setTitle("Add A new Template");
                        alert.setView(description);

                        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //What ever you want to do with the value
                                templateDescription = description.getText().toString();

                                if (templateDescription.equals("") || templateDescription.equals(" ")) {
                                    templateDescription = "Description not added";
                                    Toast desc = Toast.makeText(getActivity(), templateDescription, Toast.LENGTH_LONG);
                                    desc.show();
                                }

                                db.executeQuery("INSERT INTO tableTemplates(templateName, templateDescription, templatePowerSetting," +
                                        " templateEnabledSetting) values ('"
                                    + templateName + "','" + templateDescription + "','" + 0 + "','" + 0 + "')");
                                populateListView();
                                Toast cool = Toast.makeText(getActivity(), "Template Added", Toast.LENGTH_LONG);
                                cool.show();
                            }
                        });
                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        });
                        alert.show();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert.show();
            }
        });


        templatesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
                template = templates.get(position);
                ((MainActivity)getActivity()).setTemplate(template);
                mFragment = new ViewTemplate();
                attachFragment();
            }
        });

        templatesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long id) {
                template = templates.get(position);
                AlertDialog.Builder options = new AlertDialog.Builder(getActivity());
                options.setTitle(getActivity().getString(R.string.options));
                options.setItems(new String[]{"Delete", "Modify", "Cancel"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {
                            case 0:
                                String delQuery = "DELETE FROM tableTemplates WHERE templateName='"+template.getTemplateName()+"' ";
                                db.executeQuery(delQuery);
                                populateListView();
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                        }
                    }
                });
                options.show();
                return true;
            }
        });
    }

    // Our custom method to attach/replace Fragments
    private void attachFragment() {
        if (mFragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, mFragment, "template")
                    .addToBackStack("template").commit();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void populateListView() {
        templates.clear();
        final String query = "SELECT * FROM tableTemplates ";
        Cursor c1 = db.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    template = new Template();

                    template.setTemplateName(c1.getString(c1
                            .getColumnIndex("templateName")));
                    template.setTemplateDescription(c1.getString(c1
                            .getColumnIndex("templateDescription")));
                    template.setTemplatePower(c1.getInt(c1
                            .getColumnIndex("templatePowerSetting")));
                    template.setTemplateStatus(c1.getInt(c1
                            .getColumnIndex("templateEnabledSetting")));
                    templates.add(template);

                } while (c1.moveToNext());
            }
        }
        c1.close();

        adapter = new TemplatesViewAdapter(
                getActivity(), templates, TemplateHome.this);
        templatesListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

}
