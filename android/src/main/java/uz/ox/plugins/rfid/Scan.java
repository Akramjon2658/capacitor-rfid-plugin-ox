package uz.ox.plugins.rfid;


import android.util.Log;

import androidx.fragment.app.Fragment;

import com.ubx.usdk.rfid.aidl.IRfidCallback;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import uz.ox.plugins.rfid.pojo.TagScan;
import uz.ox.plugins.rfid.utils.SoundTool;;

public class Scan extends Fragment {
    private final RFID rfid;
    private final RFIDPlugin rfidPlugin;

    private boolean soundOnSearch = false;

    public Scan(RFID rfidWrapper, RFIDPlugin rfidPlugin) {
        this.rfid = rfidWrapper;
        this.rfidPlugin = rfidPlugin;
    }

    public static final String TAG = "usdk scan";

    public boolean scanning = false;

    private ScanCallback callback;
    private SearchCallback searchCallback;
    private HashMap<String, Integer> searchableTagsMap;

    private List<TagScan> data;
    private HashMap<String, TagScan> mapData = new HashMap<>();

    private int tagReadTotal = 0;

    private Date lastBeepTime = new Date();

    private void inventorySingle() {
        rfid.mRfidManager.inventorySingle();
    }

    public HashMap<String, TagScan> getMapData() {
        return mapData;
    }

    public int getTagReadTotal() {
        return tagReadTotal;
    }

    public void setScanCallback() {
        if (this.rfid.mRfidManager != null) {

            if (callback == null) {
                callback = new ScanCallback();
            }
            rfid.mRfidManager.registerCallback(callback);
        }
    }

    public void setSearchCallback() {
        if (this.rfid.mRfidManager != null) {

            if (searchCallback == null) {
                searchCallback = new SearchCallback();
            }
            rfid.mRfidManager.registerCallback(searchCallback);
        }
    }

    public void startScan() {
        if (rfid.RFID_INIT_STATUS) {
            if (!this.scanning) {
                setScanCallback();
                setScanStatus(true);
            }
        } else {
            Log.d(TAG, "RFID is not initialized");
        }

    }

    public void startSearch(List<String> searchableTags, boolean soundOnSearch) {
        if (rfid.RFID_INIT_STATUS) {
            if (!this.scanning) {
                this.soundOnSearch = soundOnSearch;
                setSearchCallback();
                searchableTagsMap = new HashMap<>();
                for (String tag : searchableTags) {
                    searchableTagsMap.put(tag, 1);
                }
                setScanStatus(true);
            }
        } else {
            Log.d(TAG, "RFID is not initialized");
        }

    }

    public void stopScan() {
        if (rfid.RFID_INIT_STATUS) {
            if (this.scanning) {
                rfid.mRfidManager.unregisterCallback(callback);
                mapData.clear();
                setScanStatus(false);
            }
        } else {
            Log.d(TAG, "RFID is not initialized");
        }
    }

    public void stopSearch() {
        if (rfid.RFID_INIT_STATUS) {
            if (this.scanning) {
                rfid.mRfidManager.unregisterCallback(searchCallback);
                mapData.clear();
                searchableTagsMap.clear();
                setScanStatus(false);
            }
        } else {
            Log.d(TAG, "RFID is not initialized");
        }
    }

    public void clearData() {
        mapData.clear();
    }

    public boolean getIsScanning() {
        return this.scanning;
    }

    public void setIsScanning(boolean isScanning) {
        this.scanning = isScanning;
    }

    private void setScanStatus(boolean isScan) {
        this.scanning = isScan;
        if (isScan) {
            this.tagReadTotal = 0;
            rfid.mRfidManager.startInventory((byte) 0);
            Log.v(TAG, "--- startInventory()   ----");
        } else {
            Log.v(TAG, "--- stopInventory()   ----");
            rfid.mRfidManager.stopInventory();
        }
    }

    class ScanCallback implements IRfidCallback {
        @Override
        public void onInventoryTag(String EPC, final String TID, final String strRSSI) {
            notiyDatasScan(EPC, TID, strRSSI);
        }

        /**
         * 盘存结束回调(Inventory Command Operate End)
         */
        @Override
        public void onInventoryTagEnd() {
            Log.i(TAG, "OendO onInventoryTagEnd()" + scanning);
            Log.d(TAG, "onInventoryTagEnd()");
        }
    }

    class SearchCallback implements IRfidCallback {
        @Override
        public void onInventoryTag(String EPC, final String TID, final String strRSSI) {
            notiyDatasSearch(EPC, TID, strRSSI);
        }

        /**
         * 盘存结束回调(Inventory Command Operate End)
         */
        @Override
        public void onInventoryTagEnd() {
            Log.i(TAG, "OendO onInventoryTagEnd()" + scanning);
            Log.d(TAG, "onInventoryTagEnd()");
        }
    }

    private void notiyDatasScan(String s2, String TID, final String strRSSI) {
        final String mapContainStrFinal = s2 + TID;
        if (new Date().getTime() - lastBeepTime.getTime() > 200) {
            SoundTool.getInstance(BaseApplication.getContext()).playBeep(2);
            lastBeepTime = new Date();
        }
        if (mapData.containsKey(mapContainStrFinal)) {
            TagScan tagScan = mapData.get(mapContainStrFinal);
            tagScan.setCount(mapData.get(mapContainStrFinal).getCount() + 1);
            tagScan.setRssi(strRSSI);
            mapData.put(mapContainStrFinal, tagScan);
        } else {
            rfid.mDataParents.add(s2);

            TagScan tagScan = new TagScan(s2, TID, strRSSI, 1);
            mapData.put(mapContainStrFinal, tagScan);
            rfid.tagScanSpinner.add(tagScan);
            rfidPlugin.emitScanEvent(tagScan);
        }

        tagReadTotal += 1;
    }

    private void notiyDatasSearch(String s2, String TID, final String strRSSI) {
        if (searchableTagsMap.containsKey(s2)) {
            if (this.soundOnSearch && new Date().getTime() - lastBeepTime.getTime() > 40) {
                SoundTool.getInstance(BaseApplication.getContext()).playBeep(2);
                lastBeepTime = new Date();
            }

            TagScan tagScan = new TagScan(s2, TID, strRSSI, 1);
            rfidPlugin.emitSearchEvent(tagScan);
        }
    }
}