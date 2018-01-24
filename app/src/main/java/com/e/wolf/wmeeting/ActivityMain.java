package com.e.wolf.wmeeting;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;

public class ActivityMain extends Activity {

    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager.Channel mChannel;
    private WifiP2pManager mManager;
    private WMeetingReceiver receiver;
    private boolean isWifiP2pEnabled;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareIntentFilters();
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new WMeetingReceiver();
        registerReceiver(receiver,intentFilter);
    }

    private void prepareIntentFilters(){
        //  Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

    }
    public void  setIsWifiP2pEnabled(boolean flag){
        isWifiP2pEnabled=flag;
    }

 //-------------------------------------------------------------------------------------------------
 public class WMeetingReceiver extends BroadcastReceiver {
//     private WifiP2pManager.Channel mChannel;
//     private WifiP2pManager mManager;
//     private Context mActivity;

      @Override
     public void onReceive(Context context, Intent intent) {
         String action = intent.getAction();
         if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
             // Determine if Wifi P2P mode is enabled or not, alert
             // the Activity.
             int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
             if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                 //mActivity.setIsWifiP2pEnabled(true);
             } else {
                 // activity.setIsWifiP2pEnabled(false);
             }
         } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

             // The peer list has changed!  We should probably do something about
             // that.

         } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

             // Connection state changed!  We should probably do something about
             // that.

         } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
             DeviceListFragment fragment = (DeviceListFragment) getFragmentManager().findFragmentById(R.id.device_list_fragment);
             fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));

         }
     }
 }
}
//https://stackoverflow.com/questions/13070768/android-wi-fi-direct-onpeersavailable
//http://developer-android.unlimited-translate.org/training/connect-devices-wirelessly/wifi-direct.html
//https://developer.android.com/training/connect-devices-wirelessly/wifi-direct.html

