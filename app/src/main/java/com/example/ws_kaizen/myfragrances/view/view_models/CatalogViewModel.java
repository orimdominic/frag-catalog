package com.example.ws_kaizen.myfragrances.view.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.ws_kaizen.myfragrances.database.AppDatabase;
import com.example.ws_kaizen.myfragrances.database.FragranceEntry;

import java.util.ArrayList;
import java.util.List;

public class CatalogViewModel extends AndroidViewModel {
    private LiveData<List<FragranceEntry>> fragrances;

    public CatalogViewModel(Application application){
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        fragrances = database.mFragranceDao().getAllFragrances();
    }

    public LiveData<List<FragranceEntry>> getFragrances() {
        return fragrances;
    }
}
