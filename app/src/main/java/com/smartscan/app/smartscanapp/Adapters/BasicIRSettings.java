package com.smartscan.app.smartscanapp.Adapters;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smartscan.app.smartscanapp.ConnectThread;
import com.smartscan.app.smartscanapp.Model.SendData;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.Model.Menus.Option;
import java.util.ArrayList;

/**
 * Created by Andy on 10-Dec-14.
 */
public class BasicIRSettings extends BaseAdapter {

    private Context context;
    private ArrayList<Option> optionList;
    private LayoutInflater inflater;
    private ConnectThread connectThread;
    private SendData dataSender;

    public BasicIRSettings(Context context, ArrayList<Option> list, ConnectThread connectThread) {
        this.context = context;
        this.optionList = list;
        this.connectThread = connectThread;
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
        final Option option = optionList.get(position);

        switch (option) {
            case INTENSITY:
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.setting_slider, null);
                break;

            case OUTPUT:
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
                convertView = inflater.inflate(R.layout.setting_normal, null);
                break;
            case PIRMODE:
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.setting_normal, null);
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

        if (hasSeekBar(position)) {
            final TextView barProgress = (TextView) convertView.findViewById(R.id.seekBarLevel);
            final SeekBar seekBar = (SeekBar) convertView.findViewById(R.id.seekBar);
            final Button send = (Button) convertView.findViewById(R.id.send);

            barProgress.setText("Percentage : " + seekBar.getProgress() + " / " + seekBar.getMax());

            seekBar.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {

                        int progress_value;

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            progress_value = progress;
                            barProgress.setText("Percentage : " + progress + " / " + seekBar.getMax());
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            barProgress.setText("Percentage : " + progress_value + " / " + seekBar.getMax());
                        }
                    }
            );

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String hex;

                    switch (option) {
                        case INTENSITY:
                            hex = Integer.toHexString(seekBar.getProgress());
                            dataSender = new SendData(connectThread);
                            dataSender.sendData("I1", "12"+hex);
                            Toast.makeText(context, "Setting Intensity To : " + seekBar.getProgress() + "%", Toast.LENGTH_LONG).show();
                            break;
                        case MIN:
                            hex = Integer.toHexString(seekBar.getProgress());
                            dataSender = new SendData(connectThread);
                            dataSender.sendData("I1", "12"+hex);
                            Toast.makeText(context, "Setting Min Lamp To : " + seekBar.getProgress() + "%", Toast.LENGTH_LONG).show();
                            break;
                        case SEC:
                            hex = Integer.toHexString(seekBar.getProgress());
                            dataSender = new SendData(connectThread);
                            dataSender.sendData("I1", "12"+hex);
                            Toast.makeText(context, "Setting Sec. Level To : " + seekBar.getProgress() + "%", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        if (hasButton(position)) {
            final Button send = (Button) convertView.findViewById(R.id.send);

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Setting To : " + "%", Toast.LENGTH_LONG).show();
                }
            });
        }
        return convertView;
    }

    public boolean hasSeekBar(int position) {
        Option option = optionList.get(position);
        switch (option) {
            case INTENSITY:
                return true;
            case MIN:
                return true;
            case SEC:
                return true;
            default:
                return false;
        }
    }

    public boolean hasButton(int position) {
        Option option = optionList.get(position);
        switch (option) {
            default:
                return false;
        }
    }
}
