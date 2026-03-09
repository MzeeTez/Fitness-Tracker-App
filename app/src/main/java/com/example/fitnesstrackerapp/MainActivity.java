package com.example.fitnesstrackerapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private ProgressBar progressSteps, progressCalories;
    private TextView textSteps, textCalories;
    private EditText inputExercise, inputSteps, inputCalories;
    private String todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DB
        db = AppDatabase.getDatabase(this);
        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Initialize UI components
//        progressSteps = findViewById(R.id.progressSteps);
////        progressCalories = findViewById(R.id.progressCalories);
//        textSteps = findViewById(R.id.textSteps);
//        textCalories = findViewById(R.id.textCalories);
//
//        inputExercise = findViewById(R.id.inputExercise);
//        inputSteps = findViewById(R.id.inputSteps);
//        inputCalories = findViewById(R.id.inputCalories);
//        Button btnSave = findViewById(R.id.btnSave);

        updateDashboard();

//        btnSave.setOnClickListener(v -> saveActivity());
    }

    private void saveActivity() {
        String exercise = inputExercise.getText().toString();
        String stepsStr = inputSteps.getText().toString();
        String calsStr = inputCalories.getText().toString();

        if (exercise.isEmpty() && stepsStr.isEmpty() && calsStr.isEmpty()) {
            Toast.makeText(this, "Please enter some data", Toast.LENGTH_SHORT).show();
            return;
        }

        FitnessRecord record = new FitnessRecord();
        record.date = todayDate;
        record.exerciseType = exercise;
        record.steps = stepsStr.isEmpty() ? 0 : Integer.parseInt(stepsStr);
        record.caloriesBurned = calsStr.isEmpty() ? 0 : Integer.parseInt(calsStr);

        db.fitnessDao().insert(record);
        Toast.makeText(this, "Activity Saved!", Toast.LENGTH_SHORT).show();

        // Clear inputs and refresh dashboard
        inputExercise.setText("");
        inputSteps.setText("");
        inputCalories.setText("");
        updateDashboard();
    }

    private void updateDashboard() {
        int totalSteps = db.fitnessDao().getTotalStepsForDate(todayDate);
        int totalCalories = db.fitnessDao().getTotalCaloriesForDate(todayDate);

        progressSteps.setProgress(totalSteps);
        textSteps.setText(totalSteps + " / 10000");

        progressCalories.setProgress(totalCalories);
        textCalories.setText(totalCalories + " / 2500");
    }
}