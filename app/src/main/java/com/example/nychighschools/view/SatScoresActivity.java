package com.example.nychighschools.view;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nychighschools.R;
import com.example.nychighschools.viewmodel.SchoolDataViewModel;

public class SatScoresActivity extends AppCompatActivity {

    public static final String SCHOOL_NAME_EXTRA = "school_name_extra";
    private static final String LOG_TAG = SatScoresActivity.class.getSimpleName();

    SchoolDataViewModel viewModel;
    TextView name, details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sat_scores);
        String dbn = "";
        if (getIntent() == null || (dbn = getIntent().getStringExtra(SCHOOL_NAME_EXTRA)) == null) {
            finish();
            Toast.makeText(getApplicationContext(),
                    "No DBN was received", Toast.LENGTH_LONG).show();
        }

        Log.d(LOG_TAG, "DBN is: " + dbn);
        name = findViewById(R.id.tv_name_scores);
        details = findViewById(R.id.tv_details_scores);

        viewModel = ViewModelProviders.of(this).get(SchoolDataViewModel.class);
        viewModel.getSatScores().observe(this, satScores -> {
            if (satScores.size() == 0) {
                name.setText("No data for this school");
                details.setText("");
            } else {
                name.setText(satScores.get(0).school_name);
                StringBuilder sb = new StringBuilder();
                sb.append("Number of test takers: " + satScores.get(0).num_of_sat_test_takers + '\n');
                sb.append("Reading: " + satScores.get(0).sat_critical_reading_avg_score + '\n');
                sb.append("Writing: " + satScores.get(0).sat_writing_avg_score + '\n');
                sb.append("Math: " + satScores.get(0).sat_math_avg_score + '\n');
                details.setText(sb.toString());
            }
        });
        viewModel.queryForSatScores(dbn);
    }
}
