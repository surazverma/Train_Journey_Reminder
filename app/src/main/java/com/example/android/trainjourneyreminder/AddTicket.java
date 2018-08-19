package com.example.android.trainjourneyreminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.android.trainjourneyreminder.Adapters.PassengerAdapter;
import com.example.android.trainjourneyreminder.DataModel.PNRInfo;
import com.example.android.trainjourneyreminder.DataModel.Passenger;
import com.example.android.trainjourneyreminder.Fragments.TimePickerFragment;
import com.example.android.trainjourneyreminder.NetworkUtils.PnrClient;
import com.example.android.trainjourneyreminder.Reciever.AlarmReceiver;
import com.example.android.trainjourneyreminder.database.TicketContract;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddTicket extends AppCompatActivity {

    String input;
//
    String apiKey = BuildConfig.MY_API_KEY;
    private PNRInfo pnrInfo;
    private String ticketTrainNumber;
    private String ticketTrainName;
    private String ticketBoardingPoint;
    private String ticketBoardingCode;
    private String ticketDestinationPoint;
    private String ticketDestinationCode;
    private Integer ticketNOP;
    private String ticketPnrNumber;
    private String ticketClassName;
    private String ticketClassCode;
    private String dateOfJourney;
    private Integer networkResponseCode = 0;
    private int dateValue;
    private int monthValue;
    private int yearValue;
    private int hourValue;
    private int minuteValue;
    private int reminderFieldCount;
    private boolean searchHappened = false;
    private String passengerData;
    private String reminderTimeString;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("pnr_info",pnrInfo);
        outState.putBoolean("search_Status",searchHappened);
        outState.putString("reminder_time",reminderTimeString);
    }






    @BindView(R.id.train_no_label) TextView trainNoLabel;
    @BindView(R.id.train_name_label) TextView  trainNameLabel;
    @BindView(R.id.doj_label) TextView dOJLabel;
    @BindView(R.id.passenger_no_label) TextView nOPLabel;
    @BindView(R.id.class_label) TextView classLabel;
    @BindView(R.id.s_reminder_label) TextView setReminderLabel;

    @BindView(R.id.passenger_rv) RecyclerView passengerRv;

    @BindView(R.id.reminder_layout) LinearLayout reminderView;
    @BindView(R.id.ticket_box) ConstraintLayout ticketLayout;
    @BindView(R.id.train_no) TextView trainNo;
    @BindView(R.id.train_name) TextView trainName;
    @BindView(R.id.boarding_Station) TextView boardingStationCode;
    @BindView(R.id.destination_station) TextView destinationStationCode;
    @BindView(R.id.date_of_journey) TextView dOJ;
    @BindView(R.id.passenger_no) TextView nOP;
    @BindView(R.id.journey_class) TextView className;
    @BindView(R.id.reminder_time) TextView reminderTime;
    @BindView(R.id.b_station_name) TextView bStationName;
    @BindView(R.id.d_station_name) TextView dStationName;
    @BindView(R.id.crs_label) TextView crsLabel;
    @BindView(R.id.ticket_card) CardView ticketCard;
    @BindView(R.id.pnr_input) EditText pnrInput;
    @BindView(R.id.search_button) Button searchButton;
    private List<Passenger> passengerList;
    private boolean chartStatus;




    private FirebaseAnalytics mFireBaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_ticket);
        setTitle(getString(R.string.at_title));
        ButterKnife.bind(this);



        crsLabel.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));

        bStationName.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));

        dStationName.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));

        trainNoLabel.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Play-Regular.ttf"));

        trainNo.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Play-Regular.ttf"));

        trainNameLabel.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Play-Regular.ttf"));

        trainName.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Play-Regular.ttf"));

        boardingStationCode.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));

        destinationStationCode.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));

        dOJLabel.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));

        dOJ.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));

        nOPLabel.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));

        nOP.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));

        classLabel.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));

        className.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));


        ticketCard.setVisibility(View.GONE);


        setReminderLabel.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));

        reminderTime.setTypeface(Typeface.createFromAsset(getResources().getAssets(),"Product Sans Regular.ttf"));


        mFireBaseAnalytics = FirebaseAnalytics.getInstance(this);


if (savedInstanceState != null){
    pnrInfo = savedInstanceState.getParcelable("pnr_info");
    searchHappened = savedInstanceState.getBoolean("search_Status");
    reminderTimeString = savedInstanceState.getString("reminder_time");

    if (searchHappened){
        instanciateViews(pnrInfo);
        if(!reminderTimeString.equals("--:--")){
        reminderTime.setText(reminderTimeString);}
    }
}

        reminderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(),"TimePicker");



            }
        });



        pnrInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        input = pnrInput.getText().toString();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pnrInput.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.enter_pnr,Toast.LENGTH_SHORT).show();

                }else{
                    boolean checkInDatabase = checkIfTicketSaved(pnrInput.getText().toString());
                    if (checkInDatabase){
                        Toast.makeText(AddTicket.this, R.string.already_saved, Toast.LENGTH_SHORT).show();
                    }else{

                        executeSearch(pnrInput.getText().toString());


                    }

                }
            }
        });


    }

    @Override
    public void onBackPressed() {

       if (searchHappened){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddTicket.this);
        builder.setTitle(R.string.save_ticket);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               boolean result = saveData();
               if (!result){
                   Toast.makeText(AddTicket.this, R.string.set_time, Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(AddTicket.this, R.string.ticket_saved, Toast.LENGTH_SHORT).show();
                   finish();
               }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create();
        builder.show();
    }else{
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        final Activity activity = this;
        if (itemId == R.id.save_button){





            if (networkResponseCode != 0 && networkResponseCode == 200)
            {boolean returnResult = saveData();
                if (returnResult){

                    Toast.makeText(this, R.string.ticket_saved, Toast.LENGTH_SHORT).show();
                    activity.finish();}

            }
            else {

                Toast.makeText(this, R.string.network_issue, Toast.LENGTH_SHORT).show();
            }


        }
        return super.onOptionsItemSelected(item);

    }


    private void executeSearch(String pnrNumber){


        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl("https://api.railwayapi.com/v2/pnr-status/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();



        PnrClient pnrClient = retrofit.create(PnrClient.class);

        final Call<PNRInfo> pnrInfoCall = pnrClient.searchForPnr(pnrNumber,apiKey);
        pnrInfoCall.enqueue(new Callback<PNRInfo>() {

            @Override
            public void onResponse(Call<PNRInfo> call, Response<PNRInfo> response) {
                pnrInfo = response.body();

                    networkResponseCode = pnrInfo.getResponseCode();
                if(pnrInfo.getResponseCode() == 200){
                    searchHappened = true;
                    instanciateViews(pnrInfo);
                    Toast.makeText(getApplicationContext(), R.string.success,Toast.LENGTH_SHORT).show();

                }else
                    {

                    switch (networkResponseCode){

                        case 220:
                            Crashlytics.log("Flushed Pnr");
                            Toast.makeText(getApplicationContext(), R.string.flushed_pnr, Toast.LENGTH_SHORT).show();
                            break;
                        case 221:
                            Crashlytics.log("Invalid Pnr");
                            Toast.makeText(getApplicationContext(), R.string.invalid_pnr, Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Crashlytics.log("Server Error");
                            Toast.makeText(AddTicket.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PNRInfo> call, Throwable t) {
                searchHappened = false;
                Crashlytics.log("Network Failure");
                Toast.makeText(getApplicationContext(), R.string.network_error,Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void instanciateViews(PNRInfo pnrInfo){
        ticketCard.setVisibility(View.VISIBLE);
        chartStatus = pnrInfo.getChartPrepared();
        ticketPnrNumber = pnrInfo.getPnr();
        ticketTrainNumber = pnrInfo.getTrain().getNumber();
        ticketTrainName = pnrInfo.getTrain().getName();
        ticketBoardingCode = pnrInfo.getBoardingPoint().getCode();
        ticketBoardingPoint = pnrInfo.getBoardingPoint().getName();
        ticketDestinationCode = pnrInfo.getReservationUpto().getCode();
        ticketDestinationPoint = pnrInfo.getReservationUpto().getName();
        dateOfJourney = pnrInfo.getDoj();
        ticketNOP = pnrInfo.getTotalPassengers();
        ticketClassCode = pnrInfo.getJourneyClass().getCode();
        ticketClassName = pnrInfo.getJourneyClass().getName();
        passengerList = pnrInfo.getPassengers();
        Gson gson =  new Gson();
        Type listType = new TypeToken<List<Passenger>>() {}.getType();
        passengerData = gson.toJson(passengerList,listType);




        ticketLayout.setVisibility(View.VISIBLE);
        trainNo.setText(ticketTrainNumber);
        trainName.setText(ticketTrainName);
        dOJ.setText(dateOfJourney);
        boardingStationCode.setText(ticketBoardingCode);
        destinationStationCode.setText(ticketDestinationCode);
        nOP.setText(String.valueOf(ticketNOP));
        bStationName.setText(ticketBoardingPoint);
        dStationName.setText(ticketDestinationPoint);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        passengerRv.setLayoutManager(layoutManager);
        passengerRv.setAdapter(new PassengerAdapter(getApplicationContext(),passengerList,chartStatus));

        className.setText(ticketClassName);

    }


    private void changeDateFormat(String doj){
        String date = doj.substring(0,2);
        String month = doj.substring(3,5); //dd-mm-yyyy
        String year = doj.substring(6,10);

         monthValue = Integer.parseInt(month)-1;
         dateValue = Integer.parseInt(date);
         yearValue = Integer.parseInt(year);


    }
    private void timeValue(String timeOfReminder){

        String hour = timeOfReminder.substring(0,2); //00:00
        String minute = timeOfReminder.substring(3,5);


        hourValue = Integer.parseInt(hour);
        minuteValue = Integer.parseInt(minute);
    }



    private boolean saveData(){


        reminderTimeString = reminderTime.getText().toString();


        if (reminderTimeString.equals("--:--")){
            Toast.makeText(this, R.string.pls_select_time, Toast.LENGTH_SHORT).show();
            return false;
        }else {
            reminderFieldCount = getUniqueCode();

            ContentValues values = new ContentValues();
        values.put(TicketContract.TicketEntry.COLUMN_PNR_NUMBER,ticketPnrNumber);
        values.put(TicketContract.TicketEntry.COLUMN_TRAIN_NAME,ticketTrainName);
        values.put(TicketContract.TicketEntry.COLUMN_TRAIN_NUMBER,ticketTrainNumber);
        values.put(TicketContract.TicketEntry.COLUMN_BOARDING_CODE,ticketBoardingCode);
        values.put(TicketContract.TicketEntry.COLUMN_BOARDING_NAME,ticketBoardingPoint);
        values.put(TicketContract.TicketEntry.COLUMN_DESTINATION_CODE,ticketDestinationCode);
        values.put(TicketContract.TicketEntry.COLUMN_DESTINATION_NAME,ticketDestinationPoint);
        values.put(TicketContract.TicketEntry.COLUMN_DOJ,dateOfJourney);
        values.put(TicketContract.TicketEntry.COLUMN_NUMBER_OF_PASSENGERS,ticketNOP);
        values.put(TicketContract.TicketEntry.COLUMN_JOURNEY_CLASS_NAME,ticketClassName);
        values.put(TicketContract.TicketEntry.COLUMN_JOURNEY_CLASS_CODE,ticketClassCode);
        values.put(TicketContract.TicketEntry.COLUMN_ALARM_ID,reminderFieldCount);
        values.put(TicketContract.TicketEntry.COLUMN_REMINDER_TIME,reminderTimeString);
        values.put(TicketContract.TicketEntry.COLUMN_PASSENGER_DATA,passengerData);
        values.put(TicketContract.TicketEntry.COLUMN_CHART_STATUS,chartStatus);
        Uri newUri = getContentResolver().insert(TicketContract.TicketEntry.CONTENT_URI,values);


            timeValue(reminderTimeString);
            changeDateFormat(dateOfJourney);
            NotificationUtils.addReminder(getApplicationContext(), AlarmReceiver.class,reminderFieldCount,yearValue,monthValue,dateValue,hourValue,minuteValue);
            return true;
        }

    }

    public boolean checkIfTicketSaved(String pnrNumber){
        String [] projection = {TicketContract.TicketEntry.COLUMN_PNR_NUMBER};
        Cursor cursor;
        boolean checker = false;
        cursor = getContentResolver().query(TicketContract.TicketEntry.CONTENT_URI.buildUpon().build(),projection,null,null,null);
        int pnrColumnIndex = cursor.getColumnIndex(TicketContract.TicketEntry.COLUMN_PNR_NUMBER);

        while(cursor.moveToNext()){
            String pnr = cursor.getString(pnrColumnIndex);
            if (pnr.equals(pnrNumber)){
                checker = true;
            }else
            {checker = false;}

        }
        cursor.close();
     return checker;

    }


    public int getUniqueCode(){
        String selectedCode = ticketPnrNumber.substring(4);
        Integer code = Integer.valueOf(selectedCode);
        return code;

    }
}
