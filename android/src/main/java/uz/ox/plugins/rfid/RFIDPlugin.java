package uz.ox.plugins.rfid;

import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.ubx.usdk.USDKManager;
import com.ubx.usdk.rfid.RfidManager;

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
}
