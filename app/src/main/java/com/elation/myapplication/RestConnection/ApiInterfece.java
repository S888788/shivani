package com.elation.myapplication.RestConnection;

import com.elation.myapplication.Modal.MobileNumberModal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfece {
    //Route page
    @GET("studio/api/mobile_number")
    Call<List<MobileNumberModal>> getAllMobileNumber();
    @GET("studio/api/getmobiles")
    Call<List<MobileNumberModal>> getAllSpeficeNumber(@Query("id1") String id1, @Query("id2") String id2);






}
