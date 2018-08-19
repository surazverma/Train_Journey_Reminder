package com.example.android.trainjourneyreminder.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class TicketProvider extends ContentProvider {

    private final static String LOG_TAG =  TicketProvider.class.getSimpleName();
    private final static UriMatcher sUriMatcher = buildUriMatcher();
    private TicketDbHelper ticketDbHelper;

    private static final int TICKETS = 100;
    private static final int TICKETS_WITH_ID = 200;

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority =  TicketContract.CONTENT_AUTHORITY;

        matcher.addURI(authority,TicketContract.PATH_TICKETS, TICKETS);
        matcher.addURI(authority,TicketContract.TicketEntry.TABLE_NAME+"/#", TICKETS_WITH_ID);
        return matcher;
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        ticketDbHelper = new TicketDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)){
            case TICKETS:
            {
                return TicketContract.TicketEntry.CONTENT_DIR_TYPE;
            }
            case TICKETS_WITH_ID:
            {
                return TicketContract.TicketEntry.CONTENT_ITEM_TYPE;
            }
            default:
            {throw new UnsupportedOperationException("Wrong URI type"+ uri);}
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database =  ticketDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor cursor;
        switch (match){
            case TICKETS:

                cursor = database.query(TicketContract.TicketEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case TICKETS_WITH_ID:
                selection = TicketContract.TicketEntry.COLUMN_PNR_NUMBER+"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(TicketContract.TicketEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
                default:
                    throw new IllegalArgumentException("Cannot query unknown Uri"+uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }



    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase database;
        database = ticketDbHelper.getWritableDatabase();
        Uri returnUri;
        int match = sUriMatcher.match(uri);
        switch (match){
            case TICKETS:
                long id = database.insert(TicketContract.TicketEntry.TABLE_NAME,null,values);
                if (id>0){
                    returnUri =ContentUris.withAppendedId(TicketContract.TicketEntry.CONTENT_URI,id);
                }else
                {
                    throw new SQLException("Failed to insert data with the Uri "+uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = ticketDbHelper.getWritableDatabase();
        int rowsDeleted;

        switch (sUriMatcher.match(uri)){
            case TICKETS:
                rowsDeleted = database.delete(TicketContract.TicketEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case TICKETS_WITH_ID:
                selection = TicketContract.TicketEntry.COLUMN_TICKET_ID+"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(TicketContract.TicketEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Cannot Perform Delete for this Uri"+uri);
        }
        if (rowsDeleted!=0){
             getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
       SQLiteDatabase database = ticketDbHelper.getWritableDatabase();
       int rowsUpdated;
       if (values == null){
           throw new IllegalArgumentException("values cannot be null");
       }
       switch (sUriMatcher.match(uri)){
           case TICKETS:
               rowsUpdated = database.update(TicketContract.TicketEntry.TABLE_NAME,values,selection,selectionArgs);
               break;
           case TICKETS_WITH_ID:
               selection = TicketContract.TicketEntry.COLUMN_TICKET_ID+"=?";
               selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
               rowsUpdated = database.update(TicketContract.TicketEntry.TABLE_NAME,values,selection,selectionArgs);
               Toast.makeText(getContext(), "Database Updated", Toast.LENGTH_SHORT).show();
               break;
               default:
               {
                   throw new IllegalArgumentException("Cannot update the database with the Uri");

               }

       }
        if (rowsUpdated!=0){
           getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }
}
