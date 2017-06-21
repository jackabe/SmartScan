package com.smartscan.app.smartscanapp;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartscan.app.smartscanapp.model.DeviceItem;
import com.smartscan.app.smartscanapp.model.ObjectDrawerItem;
import com.smartscan.app.smartscanapp.model.Option;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * Created by Andy on 10-Dec-14.
 */
public class OptionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Option> optionList;

    public OptionAdapter(Context context, ArrayList<Option> list) {
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

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.option_view_row, null);

        }
        TextView optionName = (TextView) convertView.findViewById(R.id.option_name);
        TextView optionDesc = (TextView) convertView.findViewById(R.id.option_info);
        ImageView optionImage = (ImageView) convertView.findViewById(R.id.option_icon);

        String name = option.getOptionName();

        switch (option) {
            case STATE: optionImage.setImageResource(R.mipmap.power);
                break;
            case MOTION: optionImage.setImageResource(R.mipmap.motion_sensor);
                break;
            case SCENES: optionImage.setImageResource(R.mipmap.forest);
                break;
            case INTENSITY: optionImage.setImageResource(R.mipmap.contrast);
                break;
            case STATUS: optionImage.setImageResource(R.mipmap.clipboard);
                break;
            case ADDRESSES: optionImage.setImageResource(R.mipmap.notebook);
                break;
            case TEMPLATES: optionImage.setImageResource(R.mipmap.web);
                break;
            case SYSTEM: optionImage.setImageResource(R.mipmap.analytics);
                break;
            case MISCELLANEOUS: optionImage.setImageResource(R.mipmap.rgb);
                break;

            // Actions for STATE

            case TURNON: optionImage.setImageResource(R.mipmap.power_button_on);
                break;
            case TURNOFF: optionImage.setImageResource(R.mipmap.power_button_off);
                break;
            case ENABLE: optionImage.setImageResource(R.mipmap.like);
                break;
            case DISABLE: optionImage.setImageResource(R.mipmap.thumb_down);
                break;

        }

        optionName.setText(name);
        optionDesc.setText(option.getOptionDesc());

        return convertView;
    }

}
