package com.alibaba.cloudpushdemo.bizactivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.cloudpushdemo.R;
import com.alibaba.cloudpushdemo.component.MyMessageIntentService;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.notification.AdvancedCustomPushNotification;
import com.alibaba.sdk.android.push.notification.BasicCustomPushNotification;
import com.alibaba.sdk.android.push.notification.CustomNotificationBuilder;

public class SettingNoticeActivity extends Activity implements View.OnClickListener {

    final String SETTING_NOTICE = "setting_notification";

    final String DEFAULT_RES_PATH_FREFIX = "android.resource://";
    final String DEFAULT_RES_SOUND_TYPE = "raw";
    final String DEFAULT_RES_ICON_TYPE = "drawable";

    final String DEFAULT_NOTICE_SOUND = "alicloud_notification_sound";
    final String ASSIGN_NOTIFCE_SOUND = "alicloud_notification_sound_assign";
    final String DEFAULT_NOTICE_LARGE_ICON = "alicloud_notification_largeicon";
    final String ASSIGN_NOTIFCE_LARGE_ICON = "alicloud_notification_largeicon_assign";
    final String DEFAULT_NOTICE_SMALL_ICON = "alicloud_notification_smallicon";
    final String ASSIGN_NOTICE_SMALL_ICON = "alicloud_notification_smallicon_assign";

    final int BASIC_CUSTOM_NOTIF_ID = 1;
    final int ADVANCED_CUSTOM_NOTIF_ID = 2;

    String PackageName;

    Button btnDefaultSoundPath;
    Button btnSetSoundPath;
    Button btnDefaultLargeIconId;
    Button btnSetLargeIconId;
    Button btnDefaultSmallIconId;
    Button btnSetSmallIconId;
    Button btnSetBasCusNotif;
    Button btnSetAdvCusNotf;
    Button btnSetIntentService;
    Button btnSetBroadcastReceiver;

    TextView tvConsoleText;

    CloudPushService mPushService;

    private void initViews() {
        setContentView(R.layout.demo_activity_notice_setting);

        // ActionBar ????????????
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnDefaultSoundPath = (Button)this.findViewById(R.id.btnSetDefSound);
        btnSetSoundPath = (Button)this.findViewById(R.id.btnSetCusSound);
        btnDefaultLargeIconId = (Button)this.findViewById(R.id.btnSetDefNotifIcon);
        btnSetLargeIconId = (Button)this.findViewById(R.id.btnSetCusNotifIcon);
        btnDefaultSmallIconId = (Button)this.findViewById(R.id.btnSetDefNotifSmallIcon);
        btnSetSmallIconId = (Button)this.findViewById(R.id.btnSetCusNotifSmallIcon);
        btnSetIntentService = (Button)this.findViewById(R.id.btnSetIntentService);
        btnSetBroadcastReceiver = (Button)this.findViewById(R.id.btnSetBroadcastReceiver);
        btnSetBasCusNotif = (Button)this.findViewById(R.id.btnSetBasicCusNotif);
        btnSetAdvCusNotf = (Button)this.findViewById(R.id.btnSetAdCusNotif);
        tvConsoleText = (TextView)this.findViewById(R.id.tvConsoleText);

        btnDefaultSoundPath.setOnClickListener(this);
        btnSetSoundPath.setOnClickListener(this);
        btnDefaultLargeIconId.setOnClickListener(this);
        btnSetLargeIconId.setOnClickListener(this);
        btnDefaultSmallIconId.setOnClickListener(this);
        btnSetSmallIconId.setOnClickListener(this);
        btnSetIntentService.setOnClickListener(this);
        btnSetBroadcastReceiver.setOnClickListener(this);
        btnSetBasCusNotif.setOnClickListener(this);
        btnSetAdvCusNotf.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PackageName = getPackageName();
        mPushService = PushServiceFactory.getCloudPushService();

        initViews();
    }

    /**
     * ??????????????????????????????
     * 1. ????????????????????????setNotificationSoundFilePath()??????????????????R.raw.alicloud_notification_sound????????????
     * ???????????????????????????????????????
     */
    private void setDefNotifSound() {
        int defaultSoundId = getResources().getIdentifier(DEFAULT_NOTICE_SOUND, DEFAULT_RES_SOUND_TYPE, PackageName);
        if (defaultSoundId != 0) {
            String defaultSoundPath = DEFAULT_RES_PATH_FREFIX + getPackageName() + "/" + defaultSoundId;
            mPushService.setNotificationSoundFilePath(defaultSoundPath);
            Log.i(SETTING_NOTICE, "Set notification sound res id to R." + DEFAULT_RES_SOUND_TYPE + "." + DEFAULT_NOTICE_SOUND);
            this.appendConsoleText("Set notification sound res id to R." + DEFAULT_RES_SOUND_TYPE + "." + DEFAULT_NOTICE_SOUND);
        } else {
            Log.e(SETTING_NOTICE, "Set notification sound path error, R."
                    + DEFAULT_RES_SOUND_TYPE + "." + DEFAULT_NOTICE_SOUND + " not found.");
            this.appendConsoleText("Set notification sound path error, R."
                    + DEFAULT_RES_SOUND_TYPE + "." + DEFAULT_NOTICE_SOUND + " not found.");
        }
    }

    /**
     * ??????????????????????????????
     * 1. ??????????????????Id???R.raw.alicloud_notification_sound_assgin???????????????
     */
    private void setCusNotifSound() {
        int assignSoundId = getResources().getIdentifier(ASSIGN_NOTIFCE_SOUND, DEFAULT_RES_SOUND_TYPE, PackageName);
        if (assignSoundId != 0) {
            String defaultSoundPath = DEFAULT_RES_PATH_FREFIX + getPackageName() + "/" + assignSoundId;
            mPushService.setNotificationSoundFilePath(defaultSoundPath);
            Log.i(SETTING_NOTICE, "Set notification sound res id to R." + DEFAULT_RES_SOUND_TYPE + "." + ASSIGN_NOTIFCE_SOUND);
            this.appendConsoleText("Set notification sound res id to R." + DEFAULT_RES_SOUND_TYPE + "." + ASSIGN_NOTIFCE_SOUND);
        } else {
            Log.e(SETTING_NOTICE, "Set notification sound path error, R."
                    + DEFAULT_RES_SOUND_TYPE + "." + ASSIGN_NOTIFCE_SOUND + " not found.");
            this.appendConsoleText("Set notification sound path error, R."
                    + DEFAULT_RES_SOUND_TYPE + "." + ASSIGN_NOTIFCE_SOUND + " not found.");
        }
    }

    /**
     * ????????????????????????????????????
     * 1. ????????????????????????setNotificationLargeIcon()??????????????????R.raw.alicloud_notification_largeicon????????????
     * ???????????????????????????????????????
     */
    private void setDefNotifIcon() {
        int defaultLargeIconId = getResources().getIdentifier(DEFAULT_NOTICE_LARGE_ICON, DEFAULT_RES_ICON_TYPE, PackageName);
        if (defaultLargeIconId != 0) {
            Drawable drawable = getApplicationContext().getResources().getDrawable(defaultLargeIconId);
            if (drawable != null) {
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                mPushService.setNotificationLargeIcon(bitmap);
                Log.i(SETTING_NOTICE, "Set notification largeIcon res id to R." + DEFAULT_RES_ICON_TYPE + "." + DEFAULT_NOTICE_LARGE_ICON);
                this.appendConsoleText("Set notification largeIcon res id to R." + DEFAULT_RES_ICON_TYPE + "." + DEFAULT_NOTICE_LARGE_ICON);
            }
        } else {
            Log.e(SETTING_NOTICE, "Set largeIcon bitmap error, R."
                    + DEFAULT_RES_ICON_TYPE + "." + DEFAULT_NOTICE_LARGE_ICON + " not found.");
            this.appendConsoleText("Set largeIcon bitmap error, R."
                    + DEFAULT_RES_ICON_TYPE + "." + DEFAULT_NOTICE_LARGE_ICON + " not found.");
        }

    }

    /**
     * ???????????????????????????
     * 1. ??????????????????Id???R.raw.alicloud_notification_largeicon_assgin???????????????
     */
    private void setCusNotifIcon() {
        int assignLargeIconId = getResources().getIdentifier(ASSIGN_NOTIFCE_LARGE_ICON, DEFAULT_RES_ICON_TYPE, PackageName);
        if (assignLargeIconId != 0) {
            Drawable drawable = getApplicationContext().getResources().getDrawable(assignLargeIconId);
            if (drawable != null) {
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                mPushService.setNotificationLargeIcon(bitmap);
                Log.i(SETTING_NOTICE, "Set notification largeIcon res id to R." + DEFAULT_RES_ICON_TYPE + "." + ASSIGN_NOTIFCE_LARGE_ICON);
                this.appendConsoleText("Set notification largeIcon res id to R." + DEFAULT_RES_ICON_TYPE + "." + ASSIGN_NOTIFCE_LARGE_ICON);
            }
        } else {
            Log.e(SETTING_NOTICE, "Set largeIcon bitmap error, R."
                    + DEFAULT_RES_ICON_TYPE + "." + ASSIGN_NOTIFCE_LARGE_ICON + " not found.");
            this.appendConsoleText("Set largeIcon bitmap error, R."
                    + DEFAULT_RES_ICON_TYPE + "." + ASSIGN_NOTIFCE_LARGE_ICON + " not found.");
        }
    }

    /**
     * ???????????????????????????????????????
     * 1. ????????????????????????setNotificationSmallIcon()??????????????????R.raw.alicloud_notification_smallicon????????????
     * ???????????????????????????????????????
     */
    private void setDefNotifSmallIcon() {
        int defaultSmallIconId = getResources().getIdentifier(DEFAULT_NOTICE_LARGE_ICON, DEFAULT_RES_ICON_TYPE, PackageName);
        if (defaultSmallIconId != 0) {
            mPushService.setNotificationSmallIcon(defaultSmallIconId);
            Log.i(SETTING_NOTICE, "Set notification smallIcon res id to R." + DEFAULT_RES_ICON_TYPE + "." + DEFAULT_NOTICE_SMALL_ICON);
            this.appendConsoleText("Set notification smallIcon res id to R." + DEFAULT_RES_ICON_TYPE + "." + DEFAULT_NOTICE_SMALL_ICON);
        } else {
            Log.e(SETTING_NOTICE, "Set notification smallIcon error, R." +
                    DEFAULT_RES_ICON_TYPE + "." + DEFAULT_NOTICE_SMALL_ICON + " not found.");
            this.appendConsoleText("Set notification smallIcon error, R." +
                    DEFAULT_RES_ICON_TYPE + "." + DEFAULT_NOTICE_SMALL_ICON + " not found.");
        }
    }

    /**
     * ????????????????????????????????????
     * 1. ??????????????????Id???R.raw.alicloud_notification_smallicon_assgin???????????????
     */
    private void setCusNotifSmallIcon() {
        int assignSmallIconId = getResources().getIdentifier(ASSIGN_NOTICE_SMALL_ICON, DEFAULT_RES_ICON_TYPE, PackageName);
        if (assignSmallIconId != 0) {
            mPushService.setNotificationSmallIcon(assignSmallIconId);
            Log.i(SETTING_NOTICE, "Set notification smallIcon res id to R." + DEFAULT_RES_ICON_TYPE + "." + ASSIGN_NOTICE_SMALL_ICON);
            this.appendConsoleText("Set notification smallIcon res id to R." + DEFAULT_RES_ICON_TYPE + "." + ASSIGN_NOTICE_SMALL_ICON);
        } else {
            Log.e(SETTING_NOTICE, "Set notification smallIcon error, R." +
                    DEFAULT_RES_ICON_TYPE + "." + ASSIGN_NOTICE_SMALL_ICON + " not found.");
            this.appendConsoleText("Set notification smallIcon error, R." +
                    DEFAULT_RES_ICON_TYPE + "." + ASSIGN_NOTICE_SMALL_ICON + " not found.");
        }
    }

    /**
     * ???????????????????????????????????????
     * 1. ??????API???????????????:https://help.aliyun.com/document_detail/30066.html#h3-3-4-basiccustompushnotification-api
     */
    private void setBasicCusNotif() {
        BasicCustomPushNotification notification = new BasicCustomPushNotification();
        notification.setRemindType(BasicCustomPushNotification.REMIND_TYPE_SOUND);//???????????????????????????
        notification.setStatusBarDrawable(R.drawable.logo_yuanjiao_120);//?????????????????????
        boolean res = CustomNotificationBuilder.getInstance().setCustomNotification(BASIC_CUSTOM_NOTIF_ID, notification);//???????????????,?????????ID???1
        this.appendConsoleText("Set Basic Notification:" + res + ", id:" + BASIC_CUSTOM_NOTIF_ID);
    }

    /**
     * ???????????????????????????????????????
     * 1. ??????API?????????:https://help.aliyun.com/document_detail/30066.html#h3-3-5-advancedcustompushnotification-api
     */
    private void setAdvCusNotf() {
        AdvancedCustomPushNotification notification = new AdvancedCustomPushNotification(R.layout.demo_notification_cus_notif, R.id.m_icon, R.id.m_title, R.id.m_text);//?????????????????????????????????,???????????????????????????????????????ID
        notification.setServerOptionFirst(true);//???????????????????????????
        notification.setBuildWhenAppInForeground(false);//???????????????????????????????????????????????????????????????
        boolean res = CustomNotificationBuilder.getInstance().setCustomNotification(ADVANCED_CUSTOM_NOTIF_ID, notification);//???????????????,?????????ID???2
        this.appendConsoleText("Set Advanced Notification:" + res + ", id:" + ADVANCED_CUSTOM_NOTIF_ID);
    }

    /**
     * ??????????????????IntentService??????
     * 1. ?????????,??????????????????????????????????????????IntentService??????
     */
    private void setIntentService() {
        mPushService.setPushIntentService(MyMessageIntentService.class);
    }

    /**
     * ??????????????????BroadcastReceiver??????
     * 1. ?????????????????????????????????????????????BroadcastReceiver
     * 2. ???????????????IntentService??????BroadcastReceiver??????????????????
     */
    private void setBroadcastReceiver() {
        mPushService.setPushIntentService(null);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            // ??????????????????
            case R.id.btnSetDefSound:
                this.setDefNotifSound();
                break;
            // ????????????????????????
            case R.id.btnSetCusSound:
                this.setCusNotifSound();
                break;
            // ???????????????????????????
            case R.id.btnSetDefNotifIcon:
                this.setDefNotifIcon();
                break;
            // ???????????????????????????
            case R.id.btnSetCusNotifIcon:
                this.setCusNotifIcon();
                break;
            // ???????????????????????????Id
            case R.id.btnSetDefNotifSmallIcon:
                this.setDefNotifSmallIcon();
                break;
            // ???????????????????????????Id
            case R.id.btnSetCusNotifSmallIcon:
                this.setCusNotifSmallIcon();
                break;
            // ???????????????????????????
            case R.id.btnSetBasicCusNotif:
                this.setBasicCusNotif();
                break;
            // ???????????????????????????????????????
            case R.id.btnSetAdCusNotif:
                this.setAdvCusNotf();
                break;
            // ????????????IntentService????????????
            case R.id.btnSetIntentService:
                this.setIntentService();
                break;
            // ??????BroadcastReceiver????????????
            case R.id.btnSetBroadcastReceiver:
                this.setBroadcastReceiver();
                break;
        }
    }

    /**
     * ????????????Activty????????????Activity
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SettingNoticeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void appendConsoleText(String text) {
        tvConsoleText.append(text + "\n");
    }

}
