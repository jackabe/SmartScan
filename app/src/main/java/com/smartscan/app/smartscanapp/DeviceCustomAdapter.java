package com.smartscan.app.smartscanapp;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartscan.app.smartscanapp.model.DeviceItem;
import com.smartscan.app.smartscanapp.model.ObjectDrawerItem;

import java.util.ArrayList;

/**
 * Created by Andy on 10-Dec-14.
 */
public class DeviceCustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DeviceItem> deviceList;


    public DeviceCustomAdapter(Context context, ArrayList<DeviceItem> list) {
        this.context = context;
        deviceList = list;
    }

    @Override
    public int getCount() {

        return deviceList.size();
    }

    @Override
    public Object getItem(int position) {

        return deviceList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        DeviceItem deviceListContent = deviceList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.device_view_row, null);

        }
        TextView deviceName = (TextView) convertView.findViewById(R.id.device_name);
        deviceName.setText(deviceListContent.getDeviceName());

        return convertView;
    }

}
