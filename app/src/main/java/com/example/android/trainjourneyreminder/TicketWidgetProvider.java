package com.example.android.trainjourneyreminder;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import static com.example.android.trainjourneyreminder.Services.UpdateWidgetService.UPDATE_ACTION;
import static com.example.android.trainjourneyreminder.Services.UpdateWidgetService.UPDATE_BOARDING_CODE;
import static com.example.android.trainjourneyreminder.Services.UpdateWidgetService.UPDATE_CLASS_NAME;
import static com.example.android.trainjourneyreminder.Services.UpdateWidgetService.UPDATE_DESTINATION_CODE;
import static com.example.android.trainjourneyreminder.Services.UpdateWidgetService.UPDATE_DOJ;
import static com.example.android.trainjourneyreminder.Services.UpdateWidgetService.UPDATE_PNR_NUMBER;
import static com.example.android.trainjourneyreminder.Services.UpdateWidgetService.UPDATE_TRAIN_NAME;

/**
 * Implementation of App Widget functionality.
 */
public class TicketWidgetProvider extends AppWidgetProvider {

    static String pnrNumber;
    static String boardingCode;
    static String destinationCode;
    static String trainName;
    static String className;
    static String dateOfJourney;

    @Override
    public void onReceive(Context context, Intent intent) {

        final String action =  intent.getAction();
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.ticket_info_widget);

        if (action.equals(UPDATE_ACTION)){

            pnrNumber = intent.getExtras().getString(UPDATE_PNR_NUMBER);
            boardingCode = intent.getExtras().getString(UPDATE_BOARDING_CODE);
            destinationCode = intent.getExtras().getString(UPDATE_DESTINATION_CODE);
            trainName = intent.getExtras().getString(UPDATE_TRAIN_NAME);
            dateOfJourney = intent.getExtras().getString(UPDATE_DOJ);
            className = intent.getExtras().getString(UPDATE_CLASS_NAME);

            boolean checker = checkValues(pnrNumber,boardingCode,destinationCode,trainName,dateOfJourney,className);
            if (checker){
                setVisibilityVISIBLE(views);
                views.setTextViewText(R.id.widget_pnr_number,pnrNumber);
                views.setTextViewText(R.id.widget_bdr_code,boardingCode);
                views.setTextViewText(R.id.widget_des_code,destinationCode);
                views.setTextViewText(R.id.widget_train_name,trainName);
                views.setTextViewText(R.id.widget_doj,dateOfJourney);
                views.setTextViewText(R.id.widget_class_code,className);
                views.setViewVisibility(R.id.empty_view, View.GONE);

            }else{
                setVisibilityGONE(views);
                views.setViewVisibility(R.id.empty_view, View.VISIBLE);
                 }

            ComponentName appWidget = new ComponentName(context,TicketWidgetProvider.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            appWidgetManager.updateAppWidget(appWidget,views);

        }
    }
    private boolean checkValues(String pnr,String bc, String dc,String tN, String doj,String cN){
        boolean checker;
        if(pnr == null&&bc == null&&dc== null&&tN== null&&doj== null&&cN== null){
            checker =  false;
        }else{
        checker =  true;}
        return checker;
    }
    private void setVisibilityGONE(RemoteViews views){
        views.setViewVisibility(R.id.widget_pnr_label, View.GONE);
        views.setViewVisibility(R.id.widget_pnr_number, View.GONE);
        views.setViewVisibility(R.id.widget_bdr_code, View.GONE);
        views.setViewVisibility(R.id.widget_des_code, View.GONE);
        views.setViewVisibility(R.id.widget_doj_label, View.GONE);
        views.setViewVisibility(R.id.widget_doj, View.GONE);
        views.setViewVisibility(R.id.widget_class_code, View.GONE);
        views.setViewVisibility(R.id.widget_class_label, View.GONE);
        views.setViewVisibility(R.id.widget_train_name_label, View.GONE);
        views.setViewVisibility(R.id.widget_train_name, View.GONE);
        views.setViewVisibility(R.id.widget_separator_line,View.GONE);
    }
    private void setVisibilityVISIBLE(RemoteViews views){
        views.setViewVisibility(R.id.widget_pnr_label, View.VISIBLE);
        views.setViewVisibility(R.id.widget_pnr_number, View.VISIBLE);
        views.setViewVisibility(R.id.widget_bdr_code, View.VISIBLE);
        views.setViewVisibility(R.id.widget_des_code, View.VISIBLE);
        views.setViewVisibility(R.id.widget_doj_label, View.VISIBLE);
        views.setViewVisibility(R.id.widget_doj, View.VISIBLE);
        views.setViewVisibility(R.id.widget_separator_line,View.VISIBLE);
        views.setViewVisibility(R.id.widget_class_code, View.VISIBLE);
        views.setViewVisibility(R.id.widget_class_label, View.VISIBLE);
        views.setViewVisibility(R.id.widget_train_name_label, View.VISIBLE);
        views.setViewVisibility(R.id.widget_train_name, View.VISIBLE);

    }
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

