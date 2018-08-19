package com.example.android.trainjourneyreminder.Reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.android.trainjourneyreminder.NotificationUtils;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        NotificationUtils.setReminder(context);

    }

}
