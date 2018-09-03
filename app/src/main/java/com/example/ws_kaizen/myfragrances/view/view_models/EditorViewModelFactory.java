package com.example.ws_kaizen.myfragrances.view.view_models;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.ws_kaizen.myfragrances.database.AppDatabase;

public class EditorViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private final AppDatabase mDb;
    private final int fragranceId;

    public EditorViewModelFactory(AppDatabase db, int id) {
        mDb = db;
        this.fragranceId = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new EditorViewModel(mDb, fragranceId);
    }
}
