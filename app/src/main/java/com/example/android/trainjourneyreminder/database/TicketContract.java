package com.example.android.trainjourneyreminder.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class TicketContract {
    public static final String CONTENT_AUTHORITY = "com.example.android.trainjourneyreminder.database";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_TICKETS = "ticket_info";

    private TicketContract(){}

    public static class TicketEntry implements BaseColumns {
        public static final String TABLE_NAME = "ticket_info";
        public static final String COLUMN_TICKET_ID = BaseColumns._ID;
        public static final String COLUMN_TRAIN_NUMBER = "train_no";
        public static final String COLUMN_TRAIN_NAME = "train_name";
        public static final String COLUMN_PNR_NUMBER = "pnr_number";
        public static final String COLUMN_BOARDING_CODE = "boarding_code";
        public static final String COLUMN_DESTINATION_CODE = "destination_code";
        public static final String COLUMN_DOJ = "date_of_journey";
        public static final String COLUMN_NUMBER_OF_PASSENGERS = "no_of_passengers";
        public static final String COLUMN_ALARM_ID = "field_count";
        public static final String COLUMN_REMINDER_TIME = "reminder_time";
        public static final String COLUMN_PASSENGER_DATA= "passenger_data";
        public static final String COLUMN_JOURNEY_CLASS_NAME = "journey_class";
        public static final String COLUMN_BOARDING_NAME = "boarding_name";
        public static final String COLUMN_DESTINATION_NAME = "destination_name";
        public static final String COLUMN_JOURNEY_CLASS_CODE= "class_code";
        public static final String COLUMN_CHART_STATUS = "chart_status";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TICKETS).build();

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_NAME;
    }
}
