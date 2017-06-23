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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.smartscan.app.smartscanapp.Database.DBConnector;
import com.smartscan.app.smartscanapp.MainActivity;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.fragments.TemplateHome;
import com.smartscan.app.smartscanapp.fragments.ViewTemplate;
import com.smartscan.app.smartscanapp.model.Option;
import com.smartscan.app.smartscanapp.model.Template;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;

public class TemplateOptionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Option> templateList;
    private String name;
    private Template template;
    private Boolean checked;
    ViewTemplate viewTemplate;

    public TemplateOptionAdapter(Context context, ArrayList<Option> list, Template template, ViewTemplate viewTemplate) {
        this.context = context;
        templateList = list;
        this.template = template;
        this.viewTemplate = viewTemplate;
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
        final Option option = templateList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.template_option_view_row, null);

        }
        convertView.setTag(option);
        TextView templateName = (TextView) convertView.findViewById(R.id.option_name);
        TextView templateDesc = (TextView) convertView.findViewById(R.id.option_info);
        SwitchCompat optionSwitch = convertView.findViewById(R.id.optionSwitch);

        name = option.getOptionName();
        String description = option.getOptionDesc();

        templateName.setText(name);
        templateDesc.setText(description);

        // Sets switches to their database values

        switch (option) {
            case TEMPLATEPOWER:
                final int power = template.getTemplatePower();
                if (power == 1) {
                    optionSwitch.setChecked(true);
                }
                break;
            case TEMPLATEENABLED:
                final int enabled = template.getTemplateStatus();
                if (enabled == 1) {
                    optionSwitch.setChecked(true);
                }
                break;
            default:
                Toast.makeText(context, "Problem!", Toast.LENGTH_SHORT).show();
        }

        // Monitors changing of switches and sets objects to be saved.
        optionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checked = true;
                    switch (option) {
                        case TEMPLATEPOWER:
                            template.setTemplatePower(1);
                            Log.i("power", "power setting to 1" + "");
                            Log.i("enable", "enable dosnt change" + "");
                            break;
                        case TEMPLATEENABLED:
                            template.setTemplateStatus(1);
                            Log.i("power", "power dosnt change" + "");
                            Log.i("enable", "enable setting to 1" + "");
                            break;
                    }
                } else {
                    checked = false;
                    switch (option) {
                        case TEMPLATEPOWER:
                            template.setTemplatePower(0);
                            Log.i("power", "power setting to 0" + "");
                            Log.i("enable", "enable dosnt change" + "");
                            break;
                        case TEMPLATEENABLED:
                            template.setTemplateStatus(0);
                            Log.i("power", "power dosnt change" + "");
                            Log.i("enable", "enable setting to 0" + "");
                            break;
                    }
                }
            }
        });

        viewTemplate.setTemplate(template);
        return convertView;
    }
}
