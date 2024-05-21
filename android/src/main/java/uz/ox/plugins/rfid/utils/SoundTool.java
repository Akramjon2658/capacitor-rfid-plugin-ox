package uz.ox.plugins.rfid.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import com.ubx.usdk.R.raw;

public class SoundTool {
    protected static volatile SoundTool instance;
    protected final int SOUND_SWIPE_CARD;
    protected final int SOUND_SWIPE_FACE;
    protected SoundPool soundPool;

    private SoundTool(Context context) {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        this.soundPool = new SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(audioAttributes)
                .build();

        this.SOUND_SWIPE_CARD = this.soundPool.load(context, raw.scan_buzzer, 1);
        this.SOUND_SWIPE_FACE = this.soundPool.load(context, raw.scan_new, 1);
    }

    public static synchronized SoundTool getInstance(Context context) {
        if (instance == null) {
            instance = new SoundTool(context);
        }

        return instance;
    }

    public void release() {
        if (this.soundPool != null) {
            this.soundPool.release();
            this.soundPool = null;
            instance = null;
        }
    }

    public void playBeep(int mode) {
        switch (mode) {
            case 0:
                return;
            case 1:
                this.soundPool.play(this.SOUND_SWIPE_CARD, 1.0F, 1.0F, 1, 0, 1.0F);
                break;
            case 2:
                this.soundPool.play(this.SOUND_SWIPE_FACE, 1.0F, 1.0F, 1, 0, 1.0F);
        }
    }
}
