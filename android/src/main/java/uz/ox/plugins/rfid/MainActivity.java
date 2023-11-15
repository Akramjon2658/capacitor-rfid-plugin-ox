package uz.ox.plugins.rfid;

import android.os.Bundle;
import android.util.Log;

import com.getcapacitor.BridgeActivity;
import com.ubx.usdk.USDKManager;
import com.ubx.usdk.rfid.RfidManager;

public class MainActivity extends BridgeActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        registerPlugin(RFIDPlugin.class);
        super.onCreate(savedInstanceState);
    }
}