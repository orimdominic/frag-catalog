package com.example.ws_kaizen.myfragrances.view.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ws_kaizen.myfragrances.R;
import com.example.ws_kaizen.myfragrances.database.AppDatabase;
import com.example.ws_kaizen.myfragrances.database.FragranceEntry;
import com.example.ws_kaizen.myfragrances.utilities.AppExecutors;
import com.example.ws_kaizen.myfragrances.view.view_models.EditorViewModel;
import com.example.ws_kaizen.myfragrances.view.view_models.EditorViewModelFactory;

public class EditorActivity extends AppCompatActivity {
    private static final String TAG = "EditorActivity";

    // Member variable for the Database
    private AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        getSupportActionBar().setTitle("Add fragrance");
        instantiateViews();

        mDb = AppDatabase.getInstance(getApplicationContext());
    }

    private void populateUI(FragranceEntry fragranceEntry) {

    }

    private void instantiateViews() {

    }


    private void saveFragrance() {

    }

    private boolean validateFields() {

        return true;
    }

    private int getGenderInt(int radioButtonId) {
        int gender = -1;
        switch (radioButtonId) {
            case R.id.rb_editor_male:
                gender = 1;
                break;
            case R.id.rb_editor_female:
                gender = 2;
                break;
            case R.id.rb_editor_unisex:
                gender = 3;
                break;
        }
        return gender;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
}
