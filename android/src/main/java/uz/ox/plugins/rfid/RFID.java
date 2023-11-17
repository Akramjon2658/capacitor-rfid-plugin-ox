package uz.ox.plugins.rfid;

import android.util.Log;

import com.getcapacitor.JSArray;
import com.ubx.usdk.RFIDSDKManager;
import com.ubx.usdk.rfid.RfidManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uz.ox.plugins.rfid.pojo.TagScan;
import uz.ox.plugins.rfid.utils.ByteUtils;

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
            String firmware =   RFIDSDKManager.getInstance().getRfidManager().getFirmwareVersion();

            Log.d("firmware version", firmware);
            Log.d(TAG, "initRfid: GetReaderType() = " + readerType);
        }else {
            Log.d(TAG, "initRfid  fail.");
        }
    }

    public boolean isConnected() {
        if(mRfidManager == null) {
            return false;
        }
        return mRfidManager.isConnected();
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

    public void clearData() {
        mDataParents.clear();
        tagScanSpinner.clear();

        scan.clearData();
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

    public int getRange() {
        return mRfidManager.getRange();
    }

    public void setRange(int range) {
        mRfidManager.setRange(range);
    }

    public int getReaderType() {
        return mRfidManager.getReaderType();
    }

    public int getQueryMode() {
        return mRfidManager.getQueryMode();
    }

    public void setQueryMode(int range) {
        mRfidManager.setQueryMode(range);
    }

    public String getFirmwareVersion() {
        return mRfidManager.getFirmwareVersion();
    }

    public int writeEpc(String epc) {

        byte[] password = {0x00, 0x00, 0x00, 0x00};

        String data = epc.replaceAll(" ", "");

        if (data.length() % 4 != 0) {// TODO data If the length is not a multiple of 4, 0 will be added automatically.
            int less = data.length() % 4;
            for (int i = 0; i < 4 - less; i++) {
                data = data + "0";
            }
        }
        byte[] d = ByteUtils.hexStringToBytes(data);

        int result = mRfidManager.writeEpc((byte) (d.length / 2), d, password);
        return result;
    }

    public int writeEpcString(String epc) {
        String password = "0000";

        int result = mRfidManager.writeEpcString(epc, password);
        return result;
    }
    public String echo(String value) {
        Log.i(TAG, value);
        return value;
    }
}
