package com.panda.flashlight.activity;

import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.panda.flashlight.R;
import com.panda.flashlight.util.Config;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;

public class IndexActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_larten;
    private boolean isOpen;
    private Camera camera = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initBmob();
        initView();
    }

    private void initBmob(){
        Bmob.initialize(this, Config.Bmob_Api);
//        BmobUpdateAgent.initAppVersion(this);
        BmobUpdateAgent.update(this);
    }

    private void initView() {
        iv_larten = (ImageView) findViewById(R.id.iv_lantern);
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
                    iv_larten.setImageResource(R.drawable.close);
                } else {
                    turnLightOn(camera);
                    isOpen = true;
                    iv_larten.setImageResource(R.drawable.open);

                }
            }
        });


    }


    @Override
    protected void onStop() {
        super.onStop();
        if (isOpen) {
            turnLightOff(camera);
            isOpen = false;
            iv_larten.setImageResource(R.drawable.close);
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
}


