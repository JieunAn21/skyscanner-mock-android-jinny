package com.jeahn.skyscanner.src.main.profile.login.interfaces;

import com.jeahn.skyscanner.src.main.profile.login.models.EmailResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginRetrofitInterface {
    @POST("/auth/email")
    Call<EmailResponse> postEmail(@Body String email);
}
