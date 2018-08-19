package com.example.android.trainjourneyreminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.trainjourneyreminder.Adapters.PassengerAdapter;
import com.example.android.trainjourneyreminder.DataModel.PNRInfo;
import com.example.android.trainjourneyreminder.DataModel.Passenger;
import com.example.android.trainjourneyreminder.NetworkUtils.PnrClient;
import com.example.android.trainjourneyreminder.Reciever.AlarmReceiver;
import com.example.android.trainjourneyreminder.Services.UpdateWidgetService;
import com.example.android.trainjourneyreminder.database.TicketContract;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TicketInfoActivity extends AppCompatActivity {


    private int hourValue;
    private int minuteValue;
    private int monthValue;
    private int dateValue;
    private int yearValue;

    private PNRInfo pnrInfo;
    private Integer networkResponseCode;
    private List<Passenger> passengers;
    private Gson gson;
    private String infoPassengerData;
    private int infoNumberOfPassengers;
    private String infoPnrNumber;
    private String infoTrainName;
    private String infoReminderTime;
    private int infoTicketIDValue;
    private  String infoBoardingCode;
    private String infoDestCode;
    private String infoDOJ;
    private String infoClassName;
    private int infoAlarmId;
    private String infoTicketID;
    private int infoTicketTrainNumber;
    private String infoBoardingPoint;
    private String infoDestinationPoint;
    private String infoClassCode;




    @BindView(R.id.reminder_time) TextView infoReminderTimeTV;
    @BindView(R.id.info_train_name) TextView infoTrainNameTV;
    @BindView(R.id.info_board_code) TextView infoBoardTV;
    @BindView(R.id.info_dest_code) TextView infoDestTV;
    @BindView(R.id.info_doj) TextView infoDOJTV;
    @BindView(R.id.info_class_name) TextView infoClassTV;
    @BindView(R.id.info_pnr_number) TextView infoPnrTV;
    @BindView(R.id.info_boarding_point) TextView infoBoardingPointTV;
    @BindView(R.id.info_destination_point) TextView infoDestinationPointTV;
    @BindView(R.id.info_passenger_rv) RecyclerView passengerRv;
    @BindView(R.id.info_class_name_label) TextView infoClassNameLabelTV;
    @BindView(R.id.info_train_number) TextView infoTrainNumberTV;

    @BindView(R.id.info_doj_label) TextView dOJLableTV;

    @BindView(R.id.info_nop_label) TextView NOPLabelTV;
    @BindView(R.id.info_nop) TextView infoNOPTV;
    @BindView(R.id.info_reserve_label) TextView infoReserveLabelTV;
    @BindView(R.id.info_s_reminder_label) TextView infoSetReminderLabelTV;
    @BindView(R.id.info_refresh_label) TextView infoRefreshLabelTV;
    @BindView(R.id.refresh_card) CardView infoRefreshCard;
    @BindView (R.id.info_delete_button) ImageView deleteButton;
    @BindView(R.id.reminder_l_layout) LinearLayout timeLayout;
    @BindView(R.id.adView) AdView mAdView;
    private boolean chartStatus;


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_info);
        ButterKnife.bind(this);
        Bundle infoBundle = getIntent().getExtras();
        chartStatus = infoBundle.getBoolean("chart_status");
        infoBoardingPoint = infoBundle.getString("boarding_point");
        infoDestinationPoint = infoBundle.getString("destination_point");
        infoPassengerData = infoBundle.getString("passenger_data");
        infoTicketTrainNumber = infoBundle.getInt("train_number");
        infoNumberOfPassengers = infoBundle.getInt("number_of_passengers");
        infoPnrNumber = infoBundle.getString("pnr_number");
        infoTrainName = infoBundle.getString("train_name");
        infoReminderTime = infoBundle.getString("reminder_time");
        infoTicketIDValue = infoBundle.getInt("ticket_id");
        infoBoardingCode = infoBundle.getString("boarding_code");
        infoDestCode = infoBundle.getString("dest_code");
        infoDOJ = infoBundle.getString("date_of_journey");
        infoClassName = infoBundle.getString("class_name");
        infoAlarmId = infoBundle.getInt("alarm_id");
        infoClassCode = infoBundle.getString("class_code");
        infoTicketID = String.valueOf(infoTicketIDValue);

        dOJLableTV.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));
        NOPLabelTV.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));
        infoReserveLabelTV.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));
        infoReminderTimeTV.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));
        infoNOPTV.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));
        infoClassNameLabelTV.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));
        gson = new Gson();
        Type listType = new TypeToken<List<Passenger>>() {
        }.getType();
        passengers = gson.fromJson(infoPassengerData, listType);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        passengerRv.setLayoutManager(layoutManager);
        passengerRv.setAdapter(new PassengerAdapter(getApplicationContext(), passengers,chartStatus));

        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        final boolean status = checkStatus(passengers, infoNumberOfPassengers);
        if (status && !chartStatus) {
            infoRefreshCard.setVisibility(View.VISIBLE);
        } else {
            infoRefreshCard.setVisibility(View.GONE);
        }


        infoPnrTV.setText(infoPnrNumber);
        infoPnrTV.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));

        infoTrainNameTV.setText(infoTrainName);
        infoTrainNameTV.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));

        infoBoardTV.setText(infoBoardingCode);
        infoBoardTV.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));

        infoDestTV.setText(infoDestCode);
        infoDestTV.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));


        infoDOJTV.setText(infoDOJ);
        infoDOJTV.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));

        infoClassTV.setText(infoClassCode);
        infoClassTV.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));
        infoBoardingPointTV.setText(infoBoardingPoint);
        infoBoardingPointTV.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));
        infoDestinationPointTV.setText(infoDestinationPoint);
        infoDestinationPointTV.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));
        infoTrainNumberTV.setText(String.valueOf(infoTicketTrainNumber));
        infoTrainNumberTV.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));
        infoNOPTV.setText(String.valueOf(infoNumberOfPassengers));



        infoReminderTimeTV.setText(infoReminderTime);
        infoReminderTimeTV.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));
        timeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(TicketInfoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hourString;
                        String minuteString;
                        if (hourOfDay<10){
                            hourString = "0"+String.valueOf(hourOfDay);
                        }else {
                            hourString = String.valueOf(hourOfDay);
                        }
                        if (minute<10){
                            minuteString = "0"+String.valueOf(minute);
                        }else{
                            minuteString = String.valueOf(minute);
                        }
                        String timeString = hourString+":"+minuteString;
                        infoReminderTimeTV.setText(timeString);

                        timeValue(timeString);
                        changeDateFormat(infoDOJ);
                        NotificationUtils.updateReminder(getApplicationContext(),AlarmReceiver.class,infoAlarmId,yearValue,monthValue,dateValue,hourValue,minuteValue,timeString,infoTicketID);
                    }
                },hour,minute,true);

                timePickerDialog.show();
//


            }
        });


        final Activity activity = this;
        infoRefreshCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status||!chartStatus)
                executeSearch(infoPnrNumber,infoTicketID);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TicketInfoActivity.this);
                builder.setMessage("Delete Ticket?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getContentResolver().delete(TicketContract.TicketEntry.CONTENT_URI.buildUpon().appendPath(infoTicketID).build(), null, null);
                        Toast.makeText(TicketInfoActivity.this, R.string.ticket_deleted, Toast.LENGTH_SHORT).show(); // make sure to delete this toast in future before sub
                        NotificationUtils.cancelReminder(getApplicationContext(), AlarmReceiver.class, infoAlarmId);
                        UpdateWidgetService.startService(getApplicationContext(), null, null, null, null, null, null);
                        activity.finish();
                    }

                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
            builder.create();
            builder.show();


            }
        });


        UpdateWidgetService.startService(this, infoPnrNumber, infoBoardingCode, infoDestCode, infoTrainName, infoDOJ, infoClassCode);

    }

    private void executeSearch(String pnrNumber, final String ticketId){
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl("https://api.railwayapi.com/v2/pnr-status/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();



        PnrClient pnrClient = retrofit.create(PnrClient.class);
        final Call<PNRInfo> pnrInfoCall = pnrClient.searchForPnr(pnrNumber,BuildConfig.MY_API_KEY);
        pnrInfoCall.enqueue(new Callback<PNRInfo>() {
            @Override
            public void onResponse(Call<PNRInfo> call, Response<PNRInfo> response) {
                pnrInfo = response.body();
                networkResponseCode = pnrInfo.getResponseCode();
                switch(networkResponseCode){
                    case 200:
                        passengers = pnrInfo.getPassengers();
                        passengerRv.setAdapter(new PassengerAdapter(getApplicationContext(),passengers,chartStatus));
                        gson = new Gson();
                        Type listType = new TypeToken<List<Passenger>>() {
                        }.getType();
                        String p_data = gson.toJson(passengers,listType);
                        ContentValues updatedPassengers = new ContentValues();
                        updatedPassengers.put(TicketContract.TicketEntry.COLUMN_PASSENGER_DATA,p_data);
                        getContentResolver().update(TicketContract.TicketEntry.CONTENT_URI.buildUpon().appendPath(ticketId).build(),updatedPassengers,ticketId,null);
                        break;
                    case 220:
                        Toast.makeText(TicketInfoActivity.this, R.string.flushed_pnr, Toast.LENGTH_SHORT).show();
                        break;
                    case 221:
                        Toast.makeText(TicketInfoActivity.this, R.string.invalid_pnr, Toast.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public void onFailure(Call<PNRInfo> call, Throwable t) {
                Toast.makeText(TicketInfoActivity.this, R.string.network_issue_call, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void timeValue(String timeOfReminder) {

        String hour = timeOfReminder.substring(0, 2);
        String minute = timeOfReminder.substring(3, 5);


        hourValue = Integer.parseInt(hour);
        minuteValue = Integer.parseInt(minute);
    }

    private void changeDateFormat(String doj) {
        String date = doj.substring(0, 2);
        String month = doj.substring(3, 5);
        String year = doj.substring(6, 10);

        monthValue = Integer.parseInt(month) - 1;
        dateValue = Integer.parseInt(date);
        yearValue = Integer.parseInt(year);


    }

    private boolean checkStatus(List<Passenger> passengers, int numberOfPassengers) {
        boolean status = false;
        for (int j = 0; j < numberOfPassengers; j++) {
            String currentStatus = passengers.get(j).getCurrentStatus();
            String cs = currentStatus.substring(0, 3);
            switch (cs) {
                case "CNF":
                    status = true;
                    break;
                case "CAN":
                    status = false;
                    break;
                case "RAC":
                    status = false;
                    break;
                case "RLW":
                    status = false;
                    break;
                case "GNW":
                    status = false;
                    break;
                case "PQW":
                    status = false;
                    break;
                case "RQW":
                    status = false;
                    break;
                case "DPW":
                    status = false;
                    break;
                case "TQW":
                    status = false;
                    break;
            }

        }
        return status;
    }



}
