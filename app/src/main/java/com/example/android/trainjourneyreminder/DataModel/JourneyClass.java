package com.example.android.trainjourneyreminder.DataModel;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JourneyClass implements Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    public final static Parcelable.Creator<JourneyClass> CREATOR = new Creator<JourneyClass>() {


        @SuppressWarnings({
                "unchecked"
        })
        public JourneyClass createFromParcel(Parcel in) {
            return new JourneyClass(in);
        }

        public JourneyClass[] newArray(int size) {
            return (new JourneyClass[size]);
        }

    }
            ;

    protected JourneyClass(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
    }

    public JourneyClass() {
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
