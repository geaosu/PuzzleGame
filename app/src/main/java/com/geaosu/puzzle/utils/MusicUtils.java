package com.geaosu.puzzle.utils;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.geaosu.puzzle.R;

public class MusicUtils {

    private static MusicUtils mInstance = null;


    // 声明一个SoundPool   // 第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
    private static SoundPool sp;//= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
    // 定义一个整型用load（）；来设置suondID   // 把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
    private static int music;//= sp.load(mContext, R.raw.click_1, 1);

    private MusicUtils(Context context) {
        sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        music = sp.load(context, R.raw.click_1, 1);
    }

    public static MusicUtils getDefault(Context context) {
        if (mInstance == null) {
            mInstance = new MusicUtils(context);
        }
        return mInstance;
    }

    public void plarMusic() {
        boolean sound = BigUtilsSP.getBoolean("sound", true);
        if (sound) {
            sp.play(music, 1, 1, 0, 0, 1);
        }
    }
}
