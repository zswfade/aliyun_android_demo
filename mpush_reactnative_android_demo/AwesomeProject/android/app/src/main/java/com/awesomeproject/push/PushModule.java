package com.awesomeproject.push;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.awesomeproject.ContVar;
import com.awesomeproject.MainApplication;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by liyazhou on 17/5/18.
 */

public class PushModule extends ReactContextBaseJavaModule {
    private static ReactContext context;

    public PushModule(ReactApplicationContext reactContext) {
        super(reactContext);
        context = reactContext;
    }

    public static ReactContext getContext() {
        return context;
    }

    public static void sendEvent(String eventName, @Nullable WritableMap params) {
        if (context == null) {
            Log.i(TAG, "reactContext==null");
        } else {
            context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName, params);
        }
        Log.d("MPS:", String.format("sendEvent: %s ", eventName) + context);
    }

    @Override
    public String getName() {
        return "MPush";
    }

    @ReactMethod
    public void getDeviceId(Callback callback) {
        callback.invoke(PushServiceFactory.getCloudPushService().getDeviceId());
    }

    @ReactMethod
    public void bindAccount(String account, final Callback callback) {
        PushServiceFactory.getCloudPushService().bindAccount(account, new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                callback.invoke("bind account success");
            }

            @Override
            public void onFailed(String s, String s1) {
                callback.invoke("bind account failed. errorCode:" + s + ", errorMsg:" + s1);
            }
        });
    }

    @ReactMethod
    public void pushInit() {
        createPrivacy();
        Context context = PushModule.context.getApplicationContext();
        if (context instanceof MainApplication) {
            ((MainApplication) context).initCloudChannel();
        }
    }

    private void createPrivacy() {
        File dataDir = ContextCompat.getDataDir(context);
        File is_privacy = new File(dataDir.getAbsolutePath(), ContVar.P_FILE);
        if (is_privacy.exists()) return;
        dataDir.mkdirs();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(is_privacy);
            try {
                fileOutputStream.write(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void unbindAccount(final Callback callback) {
        PushServiceFactory.getCloudPushService().unbindAccount(new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                callback.invoke("unbind account success");
            }

            @Override
            public void onFailed(String s, String s1) {
                callback.invoke("unbind account failed. errorCode:" + s + ", errorMsg:" + s1);
            }
        });
    }

    @ReactMethod
    public void addTag(String tag, final Callback callback) {
        PushServiceFactory.getCloudPushService().bindTag(CloudPushService.DEVICE_TARGET, new String[]{tag}, null, new CommonCallback() {

            @Override
            public void onSuccess(String s) {
                callback.invoke("add tag success");
            }

            @Override
            public void onFailed(String s, String s1) {
                callback.invoke("add tag failed. errorCode:" + s + ", errorMsg:" + s1);
            }
        });
    }

    @ReactMethod
    public void deleteTag(String tag, final Callback callback) {
        PushServiceFactory.getCloudPushService().unbindTag(CloudPushService.DEVICE_TARGET, new String[]{tag}, null, new CommonCallback() {

            @Override
            public void onSuccess(String s) {
                callback.invoke("delete tag success");
            }

            @Override
            public void onFailed(String s, String s1) {
                callback.invoke("delete tag failed. errorCode:" + s + ", errorMsg:" + s1);
            }
        });
    }

}
