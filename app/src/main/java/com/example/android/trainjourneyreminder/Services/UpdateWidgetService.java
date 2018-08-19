package com.example.android.trainjourneyreminder.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class UpdateWidgetService extends IntentService {

    public static final String UPDATE_PNR_NUMBER ="update_pnr_number";
    public static final String UPDATE_DESTINATION_CODE = "update_dest_code";
    public static final String UPDATE_BOARDING_CODE = "update_board_code";
    public static final String UPDATE_TRAIN_NAME = "update_train_name";
    public static final String UPDATE_DOJ = "update_doj";
    public static final String UPDATE_CLASS_NAME = "update_class_name";
    public static final String UPDATE_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";



    public UpdateWidgetService() {
        super("UpdateWidgetService");
    }

    public static void startService(Context context,String pnrNumber,String boardingCode,String destinationCode,String trainName,String doj,String className){
        Intent intent = new Intent(context,UpdateWidgetService.class);
        intent.putExtra(UPDATE_PNR_NUMBER,pnrNumber);
        intent.putExtra(UPDATE_DESTINATION_CODE,destinationCode);
        intent.putExtra(UPDATE_BOARDING_CODE,boardingCode);
        intent.putExtra(UPDATE_TRAIN_NAME,trainName);
        intent.putExtra(UPDATE_DOJ,doj);
        intent.putExtra(UPDATE_CLASS_NAME,className);

         context.startService(intent);

    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent!=null){
            String currentPNRnumber = intent.getExtras().getString(UPDATE_PNR_NUMBER);
            String currentBoardingCode = intent.getExtras().getString(UPDATE_BOARDING_CODE);
            String currentDestinationCode = intent.getExtras().getString(UPDATE_DESTINATION_CODE);
            String currentTrainName  = intent.getExtras().getString(UPDATE_TRAIN_NAME);
            String currentDOJ = intent.getExtras().getString(UPDATE_DOJ);
            String currentClassName = intent.getExtras().getString(UPDATE_CLASS_NAME);

            handleActionUpdate(currentPNRnumber,currentBoardingCode,currentDestinationCode,currentTrainName,currentDOJ,currentClassName);
        }
    }

    private void handleActionUpdate(String pnrNumber, String boardingCode, String destinationCode, String trainName, String doj, String className){
        Intent updateIntent = new Intent(UPDATE_ACTION);
        updateIntent.setAction(UPDATE_ACTION);
        updateIntent.putExtra(UPDATE_PNR_NUMBER,pnrNumber);
        updateIntent.putExtra(UPDATE_BOARDING_CODE,boardingCode);
        updateIntent.putExtra(UPDATE_DESTINATION_CODE,destinationCode);
        updateIntent.putExtra(UPDATE_TRAIN_NAME,trainName);
        updateIntent.putExtra(UPDATE_DOJ,doj);
        updateIntent.putExtra(UPDATE_CLASS_NAME,className);

        sendBroadcast(updateIntent);

    }
}
