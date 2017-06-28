package com.smartscan.app.smartscanapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.smartscan.app.smartscanapp.Adapters.DrawerItemCustomAdapter;
import com.smartscan.app.smartscanapp.Fragments.DeviceFragment;
import com.smartscan.app.smartscanapp.Fragments.MainFragment;
import com.smartscan.app.smartscanapp.Fragments.ScanFragment;
import com.smartscan.app.smartscanapp.Fragments.IR.StatusTextFragment;
import com.smartscan.app.smartscanapp.Fragments.IR.Templates.TemplateHome;
import com.smartscan.app.smartscanapp.Model.ObjectDrawerItem;
import com.smartscan.app.smartscanapp.Model.Templates.Template;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private Fragment mFragment;
    private BluetoothDevice device;
    private Template template;

    private ConnectThread connectThread;
    private BluetoothAdapter mBluetoothAdapter;
    private List<UUID> uuidCandidates;
    private BluetoothDevice deviceToBeSent;
    private StatusTextFragment fragment;
    EventBus myEventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myEventBus = EventBus.getDefault();

        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.smartscan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

        addDrawerItems();
        setupDrawer();

        mFragment = new MainFragment();
        attachFragment();

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mFragment = new MainFragment();
                        break;
                    case 1:
                        mFragment = new DeviceFragment();
                        break;
                    case 2:
                        mFragment = new ScanFragment();
                        break;
                    case 3:
                        mFragment = new TemplateHome();
                        break;
                    case 4:
                        mFragment = new MainFragment();
                        break;
                }
                attachFragment();
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
    }

    private void addDrawerItems() {
        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[6];

        drawerItem[0] = new ObjectDrawerItem(R.mipmap.ic_home_white_24dp, "Home");
        drawerItem[1] = new ObjectDrawerItem(R.mipmap.ic_important_devices_white_24dp, "Recent Devices");
        drawerItem[2] = new ObjectDrawerItem(R.mipmap.ic_settings_remote_white_24dp, "Start New Scan");
        drawerItem[3] = new ObjectDrawerItem(R.mipmap.ic_folder_white_24dp, "Templates");
        drawerItem[4] = new ObjectDrawerItem(R.drawable.ic_help_outline_white_24dp, "Help");
        drawerItem[5] = new ObjectDrawerItem(R.mipmap.ic_settings_white_24dp, "Settings");

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(getApplicationContext(), R.layout.listview_drawer_item_row, drawerItem);

        // Set the Adapter and the Listener on the ListView
        mDrawerList.setAdapter(adapter);

        // Set shadow and the default item selected in the ListView to be the first one
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setItemChecked(0,true);

    }

    public void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // This is where the navigation really happens.
    // We create a switch, based on the position of the Item clicked,
    // and simply change our Fragment Constant accordingly.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void connectToDevice() {

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.setName("Jack's SmartTr");
        uuidCandidates = new ArrayList<>();
        uuidCandidates.add(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        connectThread = new ConnectThread(device, true, mBluetoothAdapter, uuidCandidates);

        new Thread(new Runnable() {
            @Override
            public void run() {
                deviceToBeSent = device;
                connectThread = new ConnectThread(device, true, mBluetoothAdapter, uuidCandidates);
                try {
                    connectThread.connect();
                } catch (IOException e) {

                }
            }
        }).start();
    }

    public ConnectThread getConnection() {
        return connectThread;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Template getTemplate() {
        return template;
    }

    public Boolean isConnected() {
        if (connectThread == null ) {
            return false;
        }
        else {
            return true;
        }
    }
}

