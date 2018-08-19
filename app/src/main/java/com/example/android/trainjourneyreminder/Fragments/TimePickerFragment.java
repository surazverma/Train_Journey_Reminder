package com.example.android.trainjourneyreminder.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.android.trainjourneyreminder.R;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    TextView timeView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        return new TimePickerDialog(getActivity(),this, hour, minute,
                android.text.format.DateFormat.is24HourFormat(getActivity()));    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String hourString;
        String minuteString;
        timeView = (TextView) getActivity().findViewById(R.id.reminder_time);
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
        String time = hourString+":"+minuteString;

        timeView.setText(time);

        String a = timeView.getText().toString();

    }

}
