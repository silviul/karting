package ro.redmotor.kartgame.Sound;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import ro.redmotor.kartgame.R;

/**
 * Created by Gabi on 12/15/2015.
 * Abstraction for game sounds
 */
public class SoundPlayer {

    private SoundPool soundPool;

    private SoundPool buildSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(25)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
        }
        return soundPool;
    }

    public void loadSounds(Context context) {
        soundPool = buildSoundPool();
        if (soundPool == null) return;
        soundPool.load(context, R.raw.engine,1);
    }

    private boolean enginePlaying = false;

    public void playEngine(double rpm) {
        if (soundPool == null) return;

        if (!enginePlaying) {
            soundPool.play(1, 1, 1, 1, -1, 0.5f + (float) (rpm / 5000));
            enginePlaying = true;
        }

        soundPool.setRate(1,0.5f + (float)(rpm / 5000));
    }

    public void stopEngine() {
        if (soundPool == null) return;
        soundPool.stop(1);
        enginePlaying = false;
    }


//    public void buildBeforeAPI21(Context context) {
//
//        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//        actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//        volume = actVolume / maxVolume;
//
//        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
//
//        counter = 0;
//
//        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
//            @Override
//            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//                loaded = true;
//            }
//        });
//
//        soundID = soundPool.load(this, R.raw.fodasse, 1);
//    }
}
