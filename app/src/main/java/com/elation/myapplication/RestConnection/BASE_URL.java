package com.elation.myapplication.RestConnection;

import com.elation.myapplication.Constants;
import com.elation.myapplication.Constants2;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BASE_URL {

    private static Retrofit retrofit=null;
    private ApiInterfece mFlowerService;

    public ApiInterfece getAllnumbers() {
        if (mFlowerService == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HTTP.BASE_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mFlowerService = retrofit.create(ApiInterfece.class);
        }
        return mFlowerService;
    }
    public ApiInterfece getSpeficNumber() {
        if (mFlowerService == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants2.HTTP.BASE_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mFlowerService = retrofit.create(ApiInterfece.class);
        }
        return mFlowerService;
    }
}
