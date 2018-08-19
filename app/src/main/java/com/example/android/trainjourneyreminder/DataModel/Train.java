package com.example.android.trainjourneyreminder.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Train implements Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("number")
    @Expose
    private String number;
    public final static Parcelable.Creator<Train> CREATOR = new Creator<Train>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Train createFromParcel(Parcel in) {
            return new Train(in);
        }

        public Train[] newArray(int size) {
            return (new Train[size]);
        }

    }
            ;

    protected Train(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.number = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Train() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(number);
    }

    public int describeContents() {
        return 0;
    }

}
