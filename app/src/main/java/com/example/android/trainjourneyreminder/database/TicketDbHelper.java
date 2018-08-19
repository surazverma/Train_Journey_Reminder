package com.example.android.trainjourneyreminder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TicketDbHelper extends SQLiteOpenHelper {

   private static final  String  LOG_TAG = TicketDbHelper.class.getSimpleName();
   private static final String DATABASE_NAME = "tickets.db";
   private static final int DATABASE_VERSION = 1;


    public TicketDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE "+TicketContract.TicketEntry.TABLE_NAME +" ("+
                TicketContract.TicketEntry.COLUMN_TICKET_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TicketContract.TicketEntry.COLUMN_PNR_NUMBER+" TEXT, "
                +TicketContract.TicketEntry.COLUMN_TRAIN_NUMBER+" TEXT, "
                +TicketContract.TicketEntry.COLUMN_TRAIN_NAME+" TEXT, "
                +TicketContract.TicketEntry.COLUMN_BOARDING_CODE+" TEXT, "
                +TicketContract.TicketEntry.COLUMN_BOARDING_NAME+" TEXT, "
                +TicketContract.TicketEntry.COLUMN_DOJ+" TEXT, "
                + TicketContract.TicketEntry.COLUMN_PASSENGER_DATA+" TEXT, "
                + TicketContract.TicketEntry.COLUMN_REMINDER_TIME+" TEXT, "
                +TicketContract.TicketEntry.COLUMN_NUMBER_OF_PASSENGERS+" INTEGER, "
                + TicketContract.TicketEntry.COLUMN_CHART_STATUS+" BOOLEAN, "
                +TicketContract.TicketEntry.COLUMN_DESTINATION_NAME+" TEXT, "
                +TicketContract.TicketEntry.COLUMN_DESTINATION_CODE+" TEXT, "
                +TicketContract.TicketEntry.COLUMN_JOURNEY_CLASS_NAME+" TEXT, "
                + TicketContract.TicketEntry.COLUMN_ALARM_ID+" INTEGER, "
                + TicketContract.TicketEntry.COLUMN_JOURNEY_CLASS_CODE+" TEXT); ";
        db.execSQL(SQL_CREATE_MOVIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TicketContract.TicketEntry.TABLE_NAME );
        onCreate(db);
    }
}
