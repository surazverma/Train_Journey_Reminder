package com.example.android.trainjourneyreminder.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.trainjourneyreminder.R;
import com.example.android.trainjourneyreminder.TicketInfoActivity;
import com.example.android.trainjourneyreminder.database.TicketContract;

public class TicketCursorAdapter extends CursorAdapter {
    private Cursor mCursor;

    private Context mContext;

    public TicketCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.ticket_card_layout,parent,false);

    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView pnrTV = view.findViewById(R.id.pnr_number_mc);
        TextView b_TV = view.findViewById(R.id.b_code);
        TextView d_TV= view.findViewById(R.id.d_code);
        TextView t_name_TV = view.findViewById(R.id.t_name);
        TextView c_name_TV = view.findViewById(R.id.c_name);
        TextView doj_TV = view.findViewById(R.id.doj);

        TextView pnrLabel = view.findViewById(R.id.super_text);
        TextView tNameLabel = view.findViewById(R.id.t_name_label);
        TextView dojLabel = view.findViewById(R.id.d_label);
        TextView classLabel = view.findViewById(R.id.c_label);


        int pnrIndex = cursor.getColumnIndex(TicketContract.TicketEntry.COLUMN_PNR_NUMBER);
        int trainNameIndex = cursor.getColumnIndex(TicketContract.TicketEntry.COLUMN_TRAIN_NAME);
        int ticketIDIndex = cursor.getColumnIndex(TicketContract.TicketEntry.COLUMN_TICKET_ID);
        int boardingCodeIndex = cursor.getColumnIndex(TicketContract.TicketEntry.COLUMN_BOARDING_CODE);
        int destCodeIndex = cursor.getColumnIndex(TicketContract.TicketEntry.COLUMN_DESTINATION_CODE);
        int dateOfJourneyIndex = cursor.getColumnIndex(TicketContract.TicketEntry.COLUMN_DOJ);
        int classNameIndex = cursor.getColumnIndex(TicketContract.TicketEntry.COLUMN_JOURNEY_CLASS_NAME);
        int alarmIdIndex = cursor.getColumnIndex(TicketContract.TicketEntry.COLUMN_ALARM_ID);
        int reminderTimeIndex = cursor.getColumnIndex(TicketContract.TicketEntry.COLUMN_REMINDER_TIME);
        int passengerDataIndex = cursor.getColumnIndex(TicketContract.TicketEntry.COLUMN_PASSENGER_DATA);
        int nOPIndex = cursor.getColumnIndex(TicketContract.TicketEntry.COLUMN_NUMBER_OF_PASSENGERS);
        int classCodeIndex = cursor.getColumnIndex(TicketContract.TicketEntry.COLUMN_JOURNEY_CLASS_CODE);
        int trainNumberIndex = cursor.getColumnIndex(TicketContract.TicketEntry.COLUMN_TRAIN_NUMBER);
        int boardPointIndex = cursor.getColumnIndex(TicketContract.TicketEntry.COLUMN_BOARDING_NAME);
        int destinationPointIndex = cursor.getColumnIndex(TicketContract.TicketEntry.COLUMN_DESTINATION_NAME);


        final int numberOfPassengers = cursor.getInt(nOPIndex);
        final String passengerData = cursor.getString(passengerDataIndex);
        final String reminderTime = cursor.getString(reminderTimeIndex);
        final int alarmID = cursor.getInt(alarmIdIndex);
        final int ticketID = cursor.getInt(ticketIDIndex);
        final String pnrNumber = cursor.getString(pnrIndex);
        final String trainName = cursor.getString(trainNameIndex);
        final String boardingCode = cursor.getString(boardingCodeIndex);
        final String destCode = cursor.getString(destCodeIndex);
        final String doj = cursor.getString(dateOfJourneyIndex);
        final String className = cursor.getString(classNameIndex);
        final int trainNumber = cursor.getInt(trainNumberIndex);
        final String boardingPoint = cursor.getString(boardPointIndex);
        final String destinationPoint = cursor.getString(destinationPointIndex);




        Typeface overlineFont = Typeface.createFromAsset(view.getContext().getAssets(),context.getString(R.string.play_reg));
        Typeface standardFont = Typeface.createFromAsset(view.getContext().getAssets(),context.getString(R.string.product_sans));
        final String classCode = cursor.getString(classCodeIndex);


        pnrTV.setText(pnrNumber);
        pnrTV.setTypeface(overlineFont);
        b_TV.setText(boardingCode);
        b_TV.setTypeface(standardFont);
        d_TV.setText(destCode);
        d_TV.setTypeface(standardFont);
        t_name_TV.setText(trainName);
        t_name_TV.setTypeface(standardFont);

        if(className!=null)
        {
        c_name_TV.setText(className);
        }else
            {
            c_name_TV.setText(classCode);
        }
        c_name_TV.setTypeface(standardFont);
        doj_TV.setText(doj);
        doj_TV.setTypeface(standardFont);

        pnrLabel.setTypeface(overlineFont);
        tNameLabel.setTypeface(standardFont);
        classLabel.setTypeface(standardFont);
        dojLabel.setTypeface(standardFont);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ticketInfoIntent = new Intent(context, TicketInfoActivity.class);

                ticketInfoIntent.putExtra(context.getString(R.string.destination_point),destinationPoint);
                ticketInfoIntent.putExtra(context.getString(R.string.boarding_point),boardingPoint);
                ticketInfoIntent.putExtra(context.getString(R.string.train_number),trainNumber);
                ticketInfoIntent.putExtra(context.getString(R.string.passenger_data),passengerData);
                ticketInfoIntent.putExtra(context.getString(R.string.reminder_time_key),reminderTime);
                ticketInfoIntent.putExtra(context.getString(R.string.pnr_number),pnrNumber);
                ticketInfoIntent.putExtra(context.getString(R.string.train_name_key),trainName);
                ticketInfoIntent.putExtra(context.getString(R.string.ticket_id),ticketID);
                ticketInfoIntent.putExtra(context.getString(R.string.boarding_code),boardingCode);
                ticketInfoIntent.putExtra(context.getString(R.string.destination_code),destCode);
                ticketInfoIntent.putExtra(context.getString(R.string.date_of_journey_key),doj);
                ticketInfoIntent.putExtra(context.getString(R.string.class_name_key),className);
                ticketInfoIntent.putExtra(context.getString(R.string.class_code),classCode);
                ticketInfoIntent.putExtra(context.getString(R.string.alarm_id),alarmID);
                ticketInfoIntent.putExtra(context.getString(R.string.number_of_passengers_key),numberOfPassengers);
                Activity activity = (Activity) context;

                activity.startActivity(ticketInfoIntent);
                activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


            }
        });


    }


}
