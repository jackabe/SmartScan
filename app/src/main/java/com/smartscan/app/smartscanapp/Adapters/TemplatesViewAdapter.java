package com.smartscan.app.smartscanapp.Adapters;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartscan.app.smartscanapp.Database.DBConnector;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.fragments.TemplateHome;
import com.smartscan.app.smartscanapp.model.Option;
import com.smartscan.app.smartscanapp.model.Template;

import java.util.ArrayList;

public class TemplatesViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Template> templateList;
    private DBConnector dbConnector;
    private String name;
    private TemplateHome fragment;

    public TemplatesViewAdapter(Context context, ArrayList<Template> list, TemplateHome fragment) {
        this.context = context;
        templateList = list;
        this.fragment = fragment;
        dbConnector = new DBConnector(context);
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
        Template template = templateList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.option_view_row, null);

        }
        final TextView templateName = (TextView) convertView.findViewById(R.id.option_name);
        TextView templateDesc = (TextView) convertView.findViewById(R.id.option_info);
        ImageView templateImage = (ImageView) convertView.findViewById(R.id.option_icon);

        name = template.getTemplateName();
        String description = template.getTemplateDescription();

        templateName.setText(name);
        templateDesc.setText(description);
        templateImage.setImageResource(R.mipmap.notebook);

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Check if the user has internet connection.
                // Give user options when they hold click.

                AlertDialog.Builder options = new AlertDialog.Builder(context);
                options.setTitle(context.getString(R.string.options));
                options.setItems(new String[]{"Delete", "Modify", "Cancel"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {
                            case 0:
                                String delQuery = "DELETE FROM tableTemplates WHERE templateName='"+name+"' ";
                                dbConnector.executeQuery(delQuery);
                                fragment.populateListView();
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                        }
                    }
                });
                options.show();
                return false;
            }
        });

        return convertView;
    }

}
