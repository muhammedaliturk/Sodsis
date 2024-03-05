package com.example.sodsis;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("find")
    Call<CityListResponse> getCityListByPrefix(
            @Query("q") String prefix,
            @Query("appid") String apiKey,
            @Query("type") String type,
            @Query("mode") String mode,
            @Query("units") String units
    );
}