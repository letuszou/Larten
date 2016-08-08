package com.panda.flashlight;

import android.app.Application;
import android.util.Log;

import com.panda.flashlight.util.Config;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * Created by admin on 2016/8/8.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        Bmob.initialize(this, Config.Bmob_Api);
//        BmobUpdateAgent.initAppVersion(this);
//        BmobUpdateAgent.update(this);
    }
}
