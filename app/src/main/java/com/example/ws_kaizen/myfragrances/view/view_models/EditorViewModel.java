package com.example.ws_kaizen.myfragrances.view.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ws_kaizen.myfragrances.database.AppDatabase;
import com.example.ws_kaizen.myfragrances.database.FragranceEntry;


public class EditorViewModel extends ViewModel {
    private LiveData <FragranceEntry> fragrance;

    public EditorViewModel(AppDatabase db, int fragranceId) {
        this.fragrance = db.mFragranceDao().getFragranceById(fragranceId);
    }

    public LiveData<FragranceEntry> getFragrance() {
        return this.fragrance;
    }
}
