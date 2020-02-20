package com.geaosu.puzzle;

import android.app.Application;

import com.geaosu.puzzle.utils.BigUtilsSP;
import com.geaosu.puzzle.utils.MusicUtils;


/**
 * 程序入口
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BigUtilsSP.init(this);
        MusicUtils.getDefault(this).plarMusic();
    }
}
