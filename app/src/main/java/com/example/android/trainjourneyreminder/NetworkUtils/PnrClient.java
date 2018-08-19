package com.example.android.trainjourneyreminder.NetworkUtils;

import com.example.android.trainjourneyreminder.DataModel.PNRInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PnrClient {

    @GET("pnr/{pnr}/apikey/{apiKey}")
    Call<PNRInfo> searchForPnr(@Path("pnr") String pnrNumber,@Path("apiKey") String apiKey);
}
