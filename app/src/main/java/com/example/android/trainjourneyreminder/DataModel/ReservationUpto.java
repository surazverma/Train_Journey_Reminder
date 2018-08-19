package com.example.android.trainjourneyreminder.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservationUpto implements Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    public final static Parcelable.Creator<ReservationUpto> CREATOR = new Creator<ReservationUpto>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ReservationUpto createFromParcel(Parcel in) {
            return new ReservationUpto(in);
        }

        public ReservationUpto[] newArray(int size) {
            return (new ReservationUpto[size]);
        }

    }
            ;

    protected ReservationUpto(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ReservationUpto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(code);
    }

    public int describeContents() {
        return 0;
    }

}
