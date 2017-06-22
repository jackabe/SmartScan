package com.smartscan.app.smartscanapp.Adapters;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.smartscan.app.smartscanapp.Database.DBConnector;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.fragments.TemplateHome;
import com.smartscan.app.smartscanapp.fragments.ViewTemplate;
import com.smartscan.app.smartscanapp.model.Option;
import com.smartscan.app.smartscanapp.model.Template;
import android.widget.CompoundButton;

import java.util.ArrayList;

public class TemplateOptionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Option> templateList;
    private String name;
    private Template template;
    private Boolean checked;

    public TemplateOptionAdapter(Context context, ArrayList<Option> list, Template template) {
        this.context = context;
        templateList = list;
        this.template = template;
    }

    @Override
    public int getCount() {

        return templateList.size();
    }

    @Override
    public Object getItem(int position) {

        return templateList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        Option option = templateList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.template_option_view_row, null);

        }
        TextView templateName = (TextView) convertView.findViewById(R.id.option_name);
        TextView templateDesc = (TextView) convertView.findViewById(R.id.option_info);
        SwitchCompat optionSwitch = convertView.findViewById(R.id.optionSwitch);

        name = option.getOptionName();
        String description = option.getOptionDesc();

        templateName.setText(name);
        templateDesc.setText(description);

        optionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked) {
                    checked = true;
                }
                else {
                    checked = false;
                }
            }
        });

        return convertView;
    }

}
