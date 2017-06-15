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

import com.smartscan.app.smartscanapp.model.ObjectDrawerItem;

/**
 * Created by Andy on 10-Dec-14.
 */
public class DrawerItemCustomAdapter extends BaseAdapter {

    Context mContext;
    int mLayoutResourceId;
    ObjectDrawerItem mData[] = null;

    public DrawerItemCustomAdapter(Context context, int layoutResourceId, ObjectDrawerItem[] data) {
        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_drawer_item_row, null);

        }
        ObjectDrawerItem objectDrawerItem = mData[position];

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.drawer_item_icon);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.drawer_item_name);

        iconImageView.setImageDrawable(convertView.getResources().getDrawable(objectDrawerItem.getIcon()));
        nameTextView.setText(objectDrawerItem.getName());

        return convertView;
    }
}