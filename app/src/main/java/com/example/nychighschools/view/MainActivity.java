package com.example.nychighschools.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.nychighschools.R;
import com.example.nychighschools.model.SchoolData;
import com.example.nychighschools.viewmodel.SchoolDataViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SchoolDataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_schoolData_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = ViewModelProviders.of(this).get(SchoolDataViewModel.class);
        viewModel.getSchoolData().observe(this, schoolData ->
                recyclerView.setAdapter(new SchoolDataAdapter(schoolData)));
        viewModel.getError().observe(this, s ->
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show() );
        viewModel.queryForSchoolData();
    }
}
