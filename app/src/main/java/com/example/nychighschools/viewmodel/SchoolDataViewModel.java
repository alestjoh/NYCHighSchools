package com.example.nychighschools.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.nychighschools.model.SatScores;
import com.example.nychighschools.model.SchoolData;
import com.example.nychighschools.model.SchoolDataApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SchoolDataViewModel extends ViewModel {

    private static final String LOG_TAG = SchoolDataViewModel.class.getSimpleName();
    private SchoolDataApi api = null;

    private MutableLiveData<List<SchoolData>> schoolData = new MutableLiveData<>();
    private MutableLiveData<List<SatScores>> satScores = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    private void initRetrofit() {
        if (api == null) {
            Log.d(LOG_TAG, "initializing retrofit...");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://data.cityofnewyork.us/resource/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api = retrofit.create(SchoolDataApi.class);
        }
    }


    public LiveData<List<SchoolData>> getSchoolData() {
        return schoolData;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void queryForSchoolData() {
        initRetrofit();

        Log.d(LOG_TAG, "beginning school data API call...");
        api.getAllSchools().enqueue(new Callback<List<SchoolData>>() {
            @Override
            public void onResponse(Call<List<SchoolData>> call, Response<List<SchoolData>> response) {
                Log.d(LOG_TAG, "Got a response");
                if (response.body() != null) {
                    schoolData.postValue(response.body());
                } else {
                    Log.e(LOG_TAG, "Response was null");
                }
            }

            @Override
            public void onFailure(Call<List<SchoolData>> call, Throwable t) {
                Log.e(LOG_TAG, "no valid response");
                error.postValue("Error getting school data");
            }
        });
    }

    public MutableLiveData<List<SatScores>> getSatScores() {
        return satScores;
    }

    public void queryForSatScores(String dbn) {
        initRetrofit();

        Log.d(LOG_TAG, "beginning SAT scores API call...");
        Log.d(LOG_TAG, "Calling: " + api.getScoreForSchool(dbn).request().url());
        api.getScoreForSchool(dbn).enqueue(new Callback<List<SatScores>>() {
            @Override
            public void onResponse(Call<List<SatScores>> call, Response<List<SatScores>> response) {
                Log.d(LOG_TAG, "Got a response");
                if (response.body() != null) {
                    satScores.postValue(response.body());
                } else {
                    Log.e(LOG_TAG, "Response was null");
                }
            }

            @Override
            public void onFailure(Call<List<SatScores>> call, Throwable t) {
                Log.e(LOG_TAG, "no valid response: " + t.getLocalizedMessage());
                error.postValue("Error getting school data");
            }
        });

    }
}
