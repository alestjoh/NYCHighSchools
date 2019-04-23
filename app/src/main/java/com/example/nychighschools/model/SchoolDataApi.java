package com.example.nychighschools.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SchoolDataApi {
    @GET("s3k6-pzi2.json")
    Call<List<SchoolData>> getAllSchools();

    @GET("f9bf-2cp4.json")
    Call<List<SatScores>> getScoreForSchool(@Query("dbn") String schoolName);
}
