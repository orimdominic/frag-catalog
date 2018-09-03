package com.example.ws_kaizen.myfragrances.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface FragranceDao {
    @Insert
    void insertFragrance(FragranceEntry fragranceEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFragrance(FragranceEntry fragranceEntry);

    @Query("SELECT * FROM fragrance ORDER BY name")
    LiveData<List<FragranceEntry>> getAllFragrances();

    @Query("SELECT * FROM fragrance WHERE id = :id")
    LiveData<FragranceEntry> getFragranceById(int id);

    @Query("DELETE FROM fragrance WHERE id IN(:ids)")
    void deleteFragranceById(List<Integer> ids);
}
