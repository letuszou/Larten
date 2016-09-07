package com.panda.flashlight.activity;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.panda.flashlight.R;
import com.panda.flashlight.util.Config;

import java.util.List;
import java.util.Random;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;

public class IndexActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_larten;
    private boolean isOpen;
    private Camera camera = null;
    private RelativeLayout rl_bg;


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 0:
                    rl_bg.setBackgroundColor(getResources().getColor(R.color.gray));
                    break;
            }


            return false;
        }
    });
    private int y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initBmob();
        initView();
    }

    private void initBmob() {
        Bmob.initialize(this, Config.Bmob_Api);
//        BmobUpdateAgent.initAppVersion(this);
        BmobUpdateAgent.update(this);
    }

    private void initView() {
        iv_larten = (ImageView) findViewById(R.id.iv_lantern);
        rl_bg = (RelativeLayout) findViewById(R.id.rl_bg);
        iv_larten.setOnClickListener(this);
        isOpen = false;
        camera = Camera.open();
    }

    private void click() {

        iv_larten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    turnLightOff(camera);
                    isOpen = false;
                    iv_larten.setImageResource(R.drawable.new_close);
                } else {
                    setBackgroudColor(999);
                    turnLightOn(camera);
                    isOpen = true;
                    iv_larten.setImageResource(R.drawable.new_open);
                }
            }
        });
        iv_larten.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mHandler.sendEmptyMessage(0);
                return false;
            }
        });


    }


    @Override
    protected void onStop() {
        super.onStop();
        if (isOpen) {
            turnLightOff(camera);
            isOpen = false;
            iv_larten.setImageResource(R.drawable.new_close);
        }

    }

    public static void turnLightOn(Camera mCamera) {
        if (mCamera == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (parameters == null) {
            return;
        }
        List<String> flashModes = parameters.getSupportedFlashModes();
        // Check if camera flash exists
        if (flashModes == null) {
            // Use the screen as a flashlight (next best thing)
            return;
        }
        String flashMode = parameters.getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_TORCH.equals(flashMode)) {
            // Turn on the flash
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
            }
        }
    }


    public static void turnLightOff(Camera mCamera) {
        if (mCamera == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (parameters == null) {
            return;
        }
        List<String> flashModes = parameters.getSupportedFlashModes();
        String flashMode = parameters.getFlashMode();
        // Check if camera flash exists
        if (flashModes == null) {
            return;
        }
        if (!Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
            // Turn off the flash
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_lantern:
                click();
                break;
        }

    }


    private void setBackgroudColor(int x) {
        Random random = new Random();
        y = random.nextInt(6);

        switch (y) {
            case 0:
                rl_bg.setBackgroundColor(getResources().getColor(R.color.yellow));
                break;
            case 1:
                rl_bg.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case 2:
                rl_bg.setBackgroundColor(getResources().getColor(R.color.light));
                break;
            case 3:
                rl_bg.setBackgroundColor(getResources().getColor(R.color.SkyBlue));
                break;
            case 4:
                rl_bg.setBackgroundColor(getResources().getColor(R.color.Pink));
                break;
            case 5:
                rl_bg.setBackgroundColor(getResources().getColor(R.color.LawnGreen));
                break;
        }

    }

}


