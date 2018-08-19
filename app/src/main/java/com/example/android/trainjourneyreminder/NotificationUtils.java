package com.example.android.trainjourneyreminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.android.trainjourneyreminder.database.TicketContract;

import java.util.Calendar;

public class NotificationUtils {

public static final String NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";
    public static void setReminder(Context context){

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mchannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mchannel);
        }

        NotificationCompat.Builder mBuilder =  new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_train_24px)
                .setContentTitle("Your Upcoming Journey")
                .setContentText("Today you have to leave for your journey")
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT< Build.VERSION_CODES.O){
            mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(100,mBuilder.build());
    }

    public static void cancelReminder(Context context,Class<?> workingClass,int fieldCount){
        Intent intent = new Intent(context,workingClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,fieldCount,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    public static void addReminder(Context context, Class<?> workingClass, int fieldCount, int year, int month, int date, int hour, int min){
        Intent intent = new Intent(context,workingClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,fieldCount,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.clear();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DATE,date);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,min);

        am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

    }

    public static void updateReminder(Context context,Class<?> workingClass,
                                      int fieldCount,
                                      int year,
                                      int month,
                                      int date,
                                      int hour,
                                      int min,
                                      String reminderTime,
                                      String ticketID
                                      ){
        Intent intent = new Intent(context,workingClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,fieldCount,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.clear();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DATE,date);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,min);

        am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

        ContentValues contentValues = new ContentValues();
       contentValues.put(TicketContract.TicketEntry.COLUMN_REMINDER_TIME,reminderTime);
        context.getContentResolver().update(TicketContract.TicketEntry.CONTENT_URI.buildUpon().appendPath(ticketID).build(),contentValues, ticketID,null);
        Toast.makeText(context, "Time updated", Toast.LENGTH_SHORT).show();

    }





}
