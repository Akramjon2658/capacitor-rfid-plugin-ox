package uz.ox.plugins.rfid;

import android.util.Log;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.ubx.usdk.USDKManager;
import com.ubx.usdk.rfid.RfidManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uz.ox.plugins.rfid.pojo.TagScan;

@CapacitorPlugin(name = "RFID")
public class RFIDPlugin extends Plugin {

    public void load() {
        implementation.init();
    }
    public void emitScanEvent(TagScan tagScan) {
        JSObject ret = new JSObject();
        ret.put("epc", tagScan.getEpc());
        ret.put("tid", tagScan.getTid());
        ret.put("rssi", tagScan.getRssi());
        Log.d("new scan data", tagScan.getEpc() + " _ " + tagScan.getRssi());
        notifyListeners("onScanEvent", ret);
    }

    private final RFID implementation = new RFID(this);

    @PluginMethod
    public void isConnected(PluginCall call) {
        boolean isConnected = implementation.isConnected();

        JSObject res = new JSObject();
        res.put("connected", isConnected);

        call.resolve(res);
    }

    @PluginMethod
    public void startScan(PluginCall call) {
        implementation.startScan();
        call.resolve();
    }

    @PluginMethod
    public void stopScan(PluginCall call) {
        implementation.stopScan();
        call.resolve();
    }

    @PluginMethod
    public void clearData(PluginCall call) {
        implementation.clearData();
        call.resolve();
    }

    @PluginMethod
    public void getScanData(PluginCall call) {
        RFID.ScanDataResult scanData = implementation.getScanData();

        List<TagScan> readTags = new ArrayList<>(scanData.scanData.values());

        JSObject ret = new JSObject();
        ret.put("totalReadCount", scanData.tagReadTotal);
        ret.put("foundTagsCount", readTags.size());
        ret.put("scanData", readTags);
        call.resolve(ret);
    }

    @PluginMethod
    public void getOutputPower(PluginCall call) {
        JSObject res = new JSObject();
        res.put("value", implementation.getOutputPower());

        call.resolve(res);
    }

    @PluginMethod
    public void setOutputPower(PluginCall call) {
        int power = call.getInt("power", 30);
        implementation.setOutputPower((byte) power);
        call.resolve();
    }

    @PluginMethod
    public void getRange(PluginCall call) {
        JSObject res = new JSObject();
        res.put("value", implementation.getRange());

        call.resolve(res);
    }

    @PluginMethod
    public void setRange(PluginCall call) {
        int range = call.getInt("range", 30);
        implementation.setRange(range);
        call.resolve();
    }

    @PluginMethod
    public void getReaderType(PluginCall call) {
        JSObject res = new JSObject();
        res.put("value", implementation.getReaderType());

        call.resolve(res);
    }

    @PluginMethod
    public void getQueryMode(PluginCall call) {
        JSObject res = new JSObject();
        res.put("value", implementation.getQueryMode());

        call.resolve(res);
    }

    @PluginMethod
    public void setQueryMode(PluginCall call) {
        int queryMode = call.getInt("queryMode", 0);
        implementation.setQueryMode(queryMode);
        call.resolve();
    }

    @PluginMethod
    public void getFirmwareVersion(PluginCall call) {
        JSObject res = new JSObject();
        res.put("value", implementation.getFirmwareVersion());

        call.resolve(res);
    }

    @PluginMethod
    public void writeEpc(PluginCall call) {
        String epc = call.getString("epc");

        int result = implementation.writeEpc(epc);

        JSObject res = new JSObject();
        res.put("value", result);

        call.resolve(res);
    }

    @PluginMethod
    public void writeEpcString(PluginCall call) {
        String epc = call.getString("epc");

        int result = implementation.writeEpcString(epc);

        JSObject res = new JSObject();
        res.put("value", result);

        call.resolve(res);
    }
}
