package com.example.android.trainjourneyreminder.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PNRInfo implements Parcelable {
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("debit")
    @Expose
    private Integer debit;
    @SerializedName("pnr")
    @Expose
    private String pnr;
    @SerializedName("doj")
    @Expose
    private String doj;
    @SerializedName("total_passengers")
    @Expose
    private Integer totalPassengers;
    @SerializedName("chart_prepared")
    @Expose
    private Boolean chartPrepared;
    @SerializedName("from_station")
    @Expose
    private FromStation fromStation;
    @SerializedName("to_station")
    @Expose
    private ToStation toStation;
    @SerializedName("boarding_point")
    @Expose
    private BoardingPoint boardingPoint;
    @SerializedName("reservation_upto")
    @Expose
    private ReservationUpto reservationUpto;
    @SerializedName("train")
    @Expose
    private Train train;
    @SerializedName("journey_class")
    @Expose
    private JourneyClass journeyClass;
    @SerializedName("passengers")
    @Expose
    private List<Passenger> passengers = null;
    public final static Parcelable.Creator<PNRInfo> CREATOR = new Creator<PNRInfo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PNRInfo createFromParcel(Parcel in) {
            return new PNRInfo(in);
        }

        public PNRInfo[] newArray(int size) {
            return (new PNRInfo[size]);
        }

    }
            ;

    protected PNRInfo(Parcel in) {
        this.responseCode = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.debit = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.pnr = ((String) in.readValue((String.class.getClassLoader())));
        this.doj = ((String) in.readValue((String.class.getClassLoader())));
        this.totalPassengers = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.chartPrepared = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.fromStation = ((FromStation) in.readValue((FromStation.class.getClassLoader())));
        this.toStation = ((ToStation) in.readValue((ToStation.class.getClassLoader())));
        this.boardingPoint = ((BoardingPoint) in.readValue((BoardingPoint.class.getClassLoader())));
        this.reservationUpto = ((ReservationUpto) in.readValue((ReservationUpto.class.getClassLoader())));
        this.train = ((Train) in.readValue((Train.class.getClassLoader())));
        this.journeyClass = ((JourneyClass) in.readValue((JourneyClass.class.getClassLoader())));
        in.readList(this.passengers, (Passenger.class.getClassLoader()));
    }

    public PNRInfo() {
    }



    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Integer getResponseCode() {
        return responseCode;
    }
    public Integer getDebit() {
        return debit;
    }

    public void setDebit(Integer debit) {
        this.debit = debit;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public Integer getTotalPassengers() {
        return totalPassengers;
    }

    public void setTotalPassengers(Integer totalPassengers) {
        this.totalPassengers = totalPassengers;
    }

    public Boolean getChartPrepared() {
        return chartPrepared;
    }

    public void setChartPrepared(Boolean chartPrepared) {
        this.chartPrepared = chartPrepared;
    }

    public FromStation getFromStation() {
        return fromStation;
    }

    public void setFromStation(FromStation fromStation) {
        this.fromStation = fromStation;
    }

    public ToStation getToStation() {
        return toStation;
    }

    public void setToStation(ToStation toStation) {
        this.toStation = toStation;
    }

    public BoardingPoint getBoardingPoint() {
        return boardingPoint;
    }

    public void setBoardingPoint(BoardingPoint boardingPoint) {
        this.boardingPoint = boardingPoint;
    }

    public ReservationUpto getReservationUpto() {
        return reservationUpto;
    }

    public void setReservationUpto(ReservationUpto reservationUpto) {
        this.reservationUpto = reservationUpto;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public JourneyClass getJourneyClass() {
        return journeyClass;
    }

    public void setJourneyClass(JourneyClass journeyClass) {
        this.journeyClass = journeyClass;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(responseCode);
        dest.writeValue(debit);
        dest.writeValue(pnr);
        dest.writeValue(doj);
        dest.writeValue(totalPassengers);
        dest.writeValue(chartPrepared);
        dest.writeValue(fromStation);
        dest.writeValue(toStation);
        dest.writeValue(boardingPoint);
        dest.writeValue(reservationUpto);
        dest.writeValue(train);
        dest.writeValue(journeyClass);
        dest.writeList(passengers);
    }

    public int describeContents() {
        return 0;
    }
}
