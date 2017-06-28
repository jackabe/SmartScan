package com.smartscan.app.smartscanapp.Fragments.IR.Templates;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartscan.app.smartscanapp.Adapters.TemplateOptionAdapter;
import com.smartscan.app.smartscanapp.ConnectThread;
import com.smartscan.app.smartscanapp.Database.DBConnector;
import com.smartscan.app.smartscanapp.MainActivity;
import com.smartscan.app.smartscanapp.Model.CommandHandler;
import com.smartscan.app.smartscanapp.Model.DataCommand;
import com.smartscan.app.smartscanapp.Model.Messages.DeviceMessageListener;
import com.smartscan.app.smartscanapp.Model.DeviceSetting;
import com.smartscan.app.smartscanapp.Model.SendCommand;
import com.smartscan.app.smartscanapp.Model.Messages.TemplateMessageSystem;
import com.smartscan.app.smartscanapp.Model.Templates.TemplateOption;
import com.smartscan.app.smartscanapp.R;
import com.smartscan.app.smartscanapp.Model.Menus.Control;
import com.smartscan.app.smartscanapp.Model.Menus.Option;
import com.smartscan.app.smartscanapp.Model.Templates.Template;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewTemplate extends Fragment {

    private Fragment mFragment;
    private Template template;
    private ListView templateOptionsView;
    private Control control;
    private ArrayList<Option> templateOptions;
    private TemplateOptionAdapter adapter;
    private Button saveButton;
    private Button applyButton;
    private DBConnector dbConnector;
    private ConnectThread connectThread;
    private int power;
    private int enable;
    private String message;
    private SendCommand sendCommand;
    private CommandHandler commandHandler;
    private Boolean commandSent;
    private DeviceMessageListener messageListener;
    private TextView description;
    private TextView status;
    private TemplateOption templateOption;
    private ArrayList<TemplateOption> templateOptionsToBeSent;

    private Option name;
    private int requested;
    private DataCommand command;
    TemplateOption option;
    private DeviceSetting deviceSetting;
    private boolean finished = false;

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

        commandHandler = new CommandHandler();
        dbConnector = new DBConnector(getActivity());
        saveButton = getActivity().findViewById(R.id.templateSaveButton);
        applyButton = getActivity().findViewById(R.id.templateApplyButton);
        control = new Control();
        templateOptions = control.populateTemplateActions();
        templateOptionsView = getActivity().findViewById(R.id.templateOptionsView);
        template = ((MainActivity) getActivity()).getTemplate();
        messageListener = new DeviceMessageListener();
        templateOptionsToBeSent = new ArrayList<>();

        populateListView();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Template updateTemplate = getTemplate();
                String templateName = updateTemplate.getTemplateName();

                power = updateTemplate.getTemplatePower();
                enable = updateTemplate.getTemplateStatus();

                ArrayList<DeviceSetting>settingsToBeSaved = new ArrayList();

                if(power == 0) {
                    deviceSetting = new DeviceSetting(Option.TEMPLATEPOWER, power, DataCommand.commandOff);
                    settingsToBeSaved.add(deviceSetting);
                }if (power == 1) {
                    deviceSetting = new DeviceSetting(Option.TEMPLATEPOWER, power, DataCommand.commandOn);
                    settingsToBeSaved.add(deviceSetting);
                } if(enable == 0) {
                    deviceSetting = new DeviceSetting(Option.TEMPLATEENABLED, enable, DataCommand.commandDisable);
                    settingsToBeSaved.add(deviceSetting);
                }if (enable == 1) {
                    deviceSetting = new DeviceSetting(Option.TEMPLATEENABLED, enable, DataCommand.commandEnable);
                    settingsToBeSaved.add(deviceSetting);
                }

                for(DeviceSetting setting : settingsToBeSaved) {
                    templateOption = new TemplateOption(setting.getOption(), setting.getSetting(), false, setting.getCommand());
                    templateOptionsToBeSent.add(templateOption);
                }

                dbConnector.updateQuery(templateName, power, enable);
                Toast.makeText(getActivity(), "Template Saved", Toast.LENGTH_LONG).show();
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((MainActivity) getActivity()).isConnected()) {
                    connectThread = (((MainActivity) getActivity()).getConnection());
                    sendCommand = new SendCommand(connectThread);

                    description = new TextView(getActivity());
                    status = new TextView(getActivity());

                    final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setMessage("Template Being Applied");
                    alert.setTitle("Attempting to send commands...");
                    description.setText("Finding device...");
                    description.setPadding(100, 0, 0, 0);

                    new Thread(new Runnable() {
                        public void run() {
                            while (templateOptionsToBeSent.size() >= 1) {
                                for (int i = 0; i < templateOptionsToBeSent.size(); i++) {
                                    option = templateOptionsToBeSent.get(i);
                                    Boolean sent = option.isSent();
                                    final String optionName = option.getOptionName().getOptionName();

                                    description.post(new Runnable() {
                                        public void run() {
                                            if (!finished) {
                                                description.setText("Setting option: " + optionName);
                                                if (templateOptionsToBeSent.size() < 1) {
                                                    finished = true;
                                                }
                                            }
                                            else {

                                            }
                                        }
                                    });

                                    while (!sent) {
                                        try {
                                            Thread.sleep(5000);
                                            sendCommand.sendCommand(option.getCommand());

                                            if (checkReceived(option.getCommand())) {
                                                option.setSent(true);
                                                templateOptionsToBeSent.remove(option);
                                                if (templateOptionsToBeSent.size() < 1) {
                                                    finished = true;
                                                    description.post(new Runnable() {
                                                        public void run() {
                                                            boolean end = false;
                                                            if (!end) {
                                                                description.setText("FINISHED");
                                                                end = true;
                                                            }
                                                        }
                                                    });
                                                }
                                                break;
                                            }

                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }
                        }
                    }).start();

                    if (finished) {
                        description.setText("FINISHED!");
                        Log.i("finished", "set text");
                        Toast.makeText(getActivity(), "Finished", Toast.LENGTH_LONG).show();
                    }

                    description.setTextSize(18);
                    alert.setView(description);
                    alert.show();
                } else {
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

    // This method will be called when a HelloWorldEvent is posted
    public void onEventMainThread(TemplateMessageSystem event) {
        // your implementation
        Log.i("test", event.getMessage());
        message = event.getMessage();
    }


    public boolean checkReceived(DataCommand command) {
        if (TextUtils.isEmpty(message)) {
            return false;
        }
        if (message.contains(command.getCommandReturn())) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onStart() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    public void setTextFinished() {
        Log.i("finished outside", finished + "");
        if (finished) {
            description.setText("FINISHED!");
            Toast.makeText(getActivity(), "Finished", Toast.LENGTH_LONG).show();
        }
    }
}
