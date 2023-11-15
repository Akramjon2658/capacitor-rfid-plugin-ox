package uz.ox.plugins.rfid;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.ubx.usdk.RFIDSDKManager;
import com.ubx.usdk.rfid.RfidManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uz.ox.plugins.rfid.pojo.TagScan;

public class RFID {
    public Scan scan;
    public RFID(RFIDPlugin rfidPlugin) {
        this.scan = new Scan(this, rfidPlugin);
    }

    public static final String TAG = "usdk";

    public boolean RFID_INIT_STATUS = false;

    public RfidManager mRfidManager;
    public List<String> mDataParents;
    public List<TagScan> tagScanSpinner;

    public int readerType = 0;

    public void init() {
        mDataParents = new ArrayList<>();
        tagScanSpinner = new ArrayList<>();

        this.initRFID();
    }

    private void initRFID() {
        // Get the RFID instance in the asynchronous callback
        RFIDSDKManager.getInstance().power(true);
        boolean connect = RFIDSDKManager.getInstance().connect();

        if (connect) {
            Log.d(TAG, "initRfid()  success.");
            RFID_INIT_STATUS = true;
            readerType =  RFIDSDKManager.getInstance().getRfidManager().getReaderType();//80为短距，其他为长距
            mRfidManager = RFIDSDKManager.getInstance().getRfidManager();
            Log.d(TAG, "initRfid: GetReaderType() = " +readerType );
        }else {
            Log.d(TAG, "initRfid  fail.");
        }
    }

    public void startScan() {
        scan.startScan();
        Log.d(TAG, "start scan");
    }

    public void stopScan() {
        mDataParents.clear();
        tagScanSpinner.clear();

        scan.stopScan();
        Log.d(TAG, "stop scan");
    }

    static class ScanDataResult {
        HashMap<String, TagScan> scanData;
        int tagReadTotal;
    }

    public ScanDataResult getScanData() {
        ScanDataResult result = new ScanDataResult();

        result.scanData = scan.getMapData();
        result.tagReadTotal = scan.getTagReadTotal();

        return result;
    }

    public int getOutputPower() {
        return mRfidManager.getOutputPower();
    }

    public void setOutputPower(byte power) {
        mRfidManager.setOutputPower(power);
    }

    public String echo(String value) {
        Log.i(TAG, value);
        return value;
    }
}
