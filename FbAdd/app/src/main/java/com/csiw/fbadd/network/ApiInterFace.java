package com.csiw.fbadd.network;

import com.csiw.fbadd.model.ImageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterFace {

    @GET("img/img.json")
    Call<List<ImageResponse>> getImageResponse();
}