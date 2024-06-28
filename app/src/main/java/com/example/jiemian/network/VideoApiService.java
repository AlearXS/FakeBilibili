package com.example.jiemian.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VideoApiService {
    @GET("video/av{videoId}")
    Call<VideoResponse> getVideoInfo(@Path("videoId") String videoId);
}
