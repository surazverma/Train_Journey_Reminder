package com.example.android.trainjourneyreminder;

import android.content.Intent;
//import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.android.trainjourneyreminder.Adapters.TicketCursorAdapter;
import com.example.android.trainjourneyreminder.database.TicketContract;

import butterknife.BindView;
import butterknife.ButterKnife;



public class UpcomingJourney extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{

    private TicketCursorAdapter ticketCursorAdapter;
    @BindView(R.id.ticket_lv) ListView ticketLV;
    @BindView(R.id.add_button) FloatingActionButton ticketButton;

    private static final int TICKET_CURSOR_LOADER_ID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_journey);
        ButterKnife.bind(this);


        ticketCursorAdapter = new TicketCursorAdapter(this,null);
        ticketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTicketIntent = new Intent(getApplicationContext(), AddTicket.class);
                startActivity(addTicketIntent);
            }
        });
        getSupportLoaderManager().initLoader(TICKET_CURSOR_LOADER_ID, null, this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().initLoader(TICKET_CURSOR_LOADER_ID, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String [] projection = {
                TicketContract.TicketEntry.COLUMN_PNR_NUMBER,
                TicketContract.TicketEntry.COLUMN_TRAIN_NAME,
                TicketContract.TicketEntry.COLUMN_TRAIN_NUMBER,
                TicketContract.TicketEntry.COLUMN_BOARDING_CODE,
                TicketContract.TicketEntry.COLUMN_BOARDING_NAME,
                TicketContract.TicketEntry.COLUMN_DESTINATION_CODE,
                TicketContract.TicketEntry.COLUMN_DESTINATION_NAME,
                TicketContract.TicketEntry.COLUMN_DOJ,
                TicketContract.TicketEntry.COLUMN_NUMBER_OF_PASSENGERS,
                TicketContract.TicketEntry.COLUMN_JOURNEY_CLASS_NAME,
                TicketContract.TicketEntry.COLUMN_JOURNEY_CLASS_CODE,
                TicketContract.TicketEntry.COLUMN_ALARM_ID,
                TicketContract.TicketEntry.COLUMN_REMINDER_TIME,
                TicketContract.TicketEntry.COLUMN_PASSENGER_DATA,
                TicketContract.TicketEntry.COLUMN_TICKET_ID,


        };

        return new CursorLoader(this,
                TicketContract.TicketEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ticketCursorAdapter.swapCursor(data);
        ticketLV.setAdapter(ticketCursorAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ticketCursorAdapter.swapCursor(null);
    }

}

