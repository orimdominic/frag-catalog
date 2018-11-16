package com.example.ws_kaizen.myfragrances.view.activities;

import android.app.AlertDialog;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ws_kaizen.myfragrances.R;
import com.example.ws_kaizen.myfragrances.database.AppDatabase;
import com.example.ws_kaizen.myfragrances.database.FragranceEntry;
import com.example.ws_kaizen.myfragrances.utilities.AppExecutors;
import com.example.ws_kaizen.myfragrances.view.adapters.FragranceAdapter;
import com.example.ws_kaizen.myfragrances.view.view_models.CatalogViewModel;

import java.util.ArrayList;
import java.util.List;


public class CatalogActivity extends AppCompatActivity {
    private static final String TAG = "CatalogActivity";

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_catalog);
        instantiateViews();
        mDb = AppDatabase.getInstance(getApplicationContext());
        setupViewModel();
    }

    private void setupViewModel() {
        CatalogViewModel viewModel = ViewModelProviders.of(this).get(CatalogViewModel.class);
        viewModel.getFragrances().observe(this, new Observer<List<FragranceEntry>>() {
            @Override
            public void onChanged(@Nullable List<FragranceEntry> fragranceEntries) {
                if (fragranceEntries != null) {
                }
            }
        });
    }

    private void instantiateViews() {
    }

    private void toggleSelectAllMenuItem(ActionMode mode) {

    }

    private void showDeleteConfirmationDialog(final ActionMode mode) {

    }


    private void deleteFragrances(ActionMode mode) {

    }

    private void requestShareFormat(final ActionMode mode) {

    }

    private void createSharableText(ActionMode mode, boolean includeGender) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        return true;
    }

}
