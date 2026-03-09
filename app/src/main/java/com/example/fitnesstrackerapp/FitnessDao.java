package com.example.fitnesstrackerapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface FitnessDao {
    @Insert
    void insert(FitnessRecord record);

    @Query("SELECT * FROM fitness_records WHERE date = :todayDate")
    List<FitnessRecord> getRecordsForDate(String todayDate);

    @Query("SELECT SUM(caloriesBurned) FROM fitness_records WHERE date = :todayDate")
    int getTotalCaloriesForDate(String todayDate);

    @Query("SELECT SUM(steps) FROM fitness_records WHERE date = :todayDate")
    int getTotalStepsForDate(String todayDate);
}