package com.smartscan.app.smartscanapp.Adapters;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.Model.Option;

import java.util.ArrayList;

/**
 * Created by Andy on 10-Dec-14.
 */
public class BasicIRSettings extends BaseAdapter {

    private Context context;
    private ArrayList<Option> optionList;
    private LayoutInflater inflater;

    public BasicIRSettings(Context context, ArrayList<Option> list) {
        this.context = context;
        optionList = list;
    }

    @Override
    public int getCount() {

        return optionList.size();
    }

    @Override
    public Object getItem(int position) {

        return optionList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        Option option = optionList.get(position);

        switch (option) {
            case INTENSITY:
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.setting_slider, null);
                break;

            case TYPE:
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.setting_normal, null);
                break;

            case BURN:
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.setting_normal, null);
                break;

            case MIN:
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.setting_slider, null);
                break;

            case MOTION:
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.setting_normal, null);
                break;
            case POWER:
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.setting_switch, null);
                break;
            case PIRENABLE:
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.setting_switch, null);
                break;
            case SEC:
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.setting_slider, null);
                break;
            default:
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.setting_normal, null);
                break;
        }

        TextView optionName = (TextView) convertView.findViewById(R.id.option_name);
        TextView optionDesc = (TextView) convertView.findViewById(R.id.option_info);
        optionName.setText(option.getOptionName());
        optionDesc.setText(option.getOptionDesc());

        return convertView;
    }

}
