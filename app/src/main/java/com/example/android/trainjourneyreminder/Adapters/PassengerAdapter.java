package com.example.android.trainjourneyreminder.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.trainjourneyreminder.DataModel.Passenger;
import com.example.android.trainjourneyreminder.R;

import java.util.List;

public class PassengerAdapter extends RecyclerView.Adapter<PassengerAdapter.ViewHolder> {

    private List<Passenger> passengers;
    private Context context;
    private boolean chartStatus;
    public PassengerAdapter(Context context,List<Passenger> passengers, boolean chartStatus){
        this.context = context;
        this.passengers = passengers;
        this.chartStatus = chartStatus;
    }

    @Override
    public PassengerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.passenger_list_item,parent,false);

          return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Integer passengerNo = passengers.get(position).getNo();
        String currentStatus = passengers.get(position).getCurrentStatus();
        boolean status = checkStatus(currentStatus);

        if (status && !chartStatus){
            currentStatus = currentStatus.substring(0,3);
        }
        String pNoInString = Integer.toString(passengerNo);
        String bookingStatusString = passengers.get(position).getBookingStatus();
        holder.passengerNumber.setText(pNoInString);
        holder.reservationStatus.setText(currentStatus);
        holder.bookingStatus.setText(bookingStatusString);


    }

    @Override
    public int getItemCount() {
        return passengers.size();
    }


    private boolean checkStatus(String currentStatus) {

            boolean status = false;
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

        return status;

    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView passengerLabel;
        TextView passengerNumber;
        TextView bookingStatus;
        TextView currentStatusOfReservation;
        TextView reservationStatus;
        TextView bs_label;
        Typeface standardFont;


        public ViewHolder(View itemView){
            super(itemView);
            standardFont = Typeface.createFromAsset(itemView.getContext().getAssets(), itemView.getContext().getString(R.string.product_sans));

            bs_label = (TextView) itemView.findViewById(R.id.bs_label);
            bs_label.setTypeface(standardFont);
            bookingStatus = (TextView) itemView.findViewById(R.id.booking_status);
            bookingStatus.setTypeface(standardFont);
            passengerLabel = (TextView) itemView.findViewById(R.id.passenger_label);
            passengerLabel.setTypeface(standardFont);

            passengerNumber = (TextView) itemView.findViewById(R.id.p_no);
            passengerNumber.setTypeface(standardFont);
            currentStatusOfReservation = (TextView) itemView.findViewById(R.id.cs_label);
            currentStatusOfReservation.setTypeface(standardFont);
            reservationStatus = (TextView) itemView.findViewById(R.id.r_status);
            reservationStatus.setTypeface(standardFont);
        }
    }
}
