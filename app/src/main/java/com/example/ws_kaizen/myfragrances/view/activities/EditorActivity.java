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

    private EditText etFragranceName, etFragrancePrice, etQuantity;
    private Button btnSave;
    private RadioGroup rgQuantityMode, rgGender;
    private RadioButton rbAdd, rbSell, rbDoNaught, rbMale, rbFemale, rbUnisex;

    private static final int DEFAULT_FRAGRANCE_ID = -1;
    // Extra for the frag ID to be received in the intent
    public static final String EXTRA_FRAGRANCE_ID = "EXTRA_FRAGRANCE_ID";
    private int mFragranceId = DEFAULT_FRAGRANCE_ID;
    public static final String INSTANCE_FRAGRANCE_ID = "INSTANCE_FRAGRANCE_ID ";

    // Member variable for the Database
    private AppDatabase mDb;
    private int initialQuantity = 0;//Quantity on opening activity
    private int initialQuantitySold;//Quantity on opening activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        getSupportActionBar().setTitle("Add fragrance");
        instantiateViews();

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_FRAGRANCE_ID)) {
            mFragranceId = savedInstanceState.getInt(INSTANCE_FRAGRANCE_ID, DEFAULT_FRAGRANCE_ID);
        }

        final Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_FRAGRANCE_ID)) {
            btnSave.setText("Update");
            getSupportActionBar().setTitle("Edit fragrance");
            if (mFragranceId == DEFAULT_FRAGRANCE_ID) {

                mFragranceId = intent.getIntExtra(EXTRA_FRAGRANCE_ID, DEFAULT_FRAGRANCE_ID);

                EditorViewModelFactory factory =
                        new EditorViewModelFactory(mDb, mFragranceId);

                final EditorViewModel viewModel =
                        ViewModelProviders.of(this, factory).get(EditorViewModel.class);

                viewModel.getFragrance().observe(this, new Observer<FragranceEntry>() {
                    @Override
                    public void onChanged(@Nullable FragranceEntry fragranceEntry) {
                        if (fragranceEntry != null) {
                            if (Integer.valueOf(fragranceEntry.getQuantityInStock()) != null) {
                                initialQuantity = fragranceEntry.getQuantityInStock();
                                initialQuantitySold = fragranceEntry.getQuantitySold();
                            } else {
                                initialQuantity = 0;
                                initialQuantitySold = 0;
                                fragranceEntry.setQuantityInStock(0);
                                fragranceEntry.setQuantitySold(0);
                            }
                            populateUI(fragranceEntry);
                        }
                    }
                });
            }
        }
    }

    private void populateUI(FragranceEntry fragranceEntry) {
        if (fragranceEntry == null) {
            return;
        }

        etFragranceName.setText(fragranceEntry.getName());
        etFragrancePrice.setText(String.valueOf(fragranceEntry.getPrice()));
        etQuantity.setText(String.valueOf(fragranceEntry.getQuantityInStock()));
        if (fragranceEntry.getGender() == 1) {
            rgGender.check(R.id.rb_editor_male);
        } else if (fragranceEntry.getGender() == 2){
            rgGender.check(R.id.rb_editor_female);
        } else if (fragranceEntry.getGender() == 3){
            rgGender.check(R.id.rb_editor_unisex);
        }

    }

    private void instantiateViews() {
        etFragranceName = findViewById(R.id.edit_fragrance_name);
        etFragrancePrice = findViewById(R.id.edit_fragrance_price);
        btnSave = findViewById(R.id.btn_save);
        etQuantity = findViewById(R.id.et_edit_fragrance_quantity);

        rgQuantityMode = findViewById(R.id.rg_editor_quantity_mode);
        rgGender = findViewById(R.id.rg_editor_gender);
        rbAdd = findViewById(R.id.rb_editor_add);
        rbSell = findViewById(R.id.rb_editor_sell);
        rbDoNaught = findViewById(R.id.rb_editor_do_naught);
        rbMale = findViewById(R.id.rb_editor_male);
        rbFemale = findViewById(R.id.rb_editor_female);
        rbUnisex = findViewById(R.id.rb_editor_unisex);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateFields()) {
                    saveFragrance();
                }

            }
        });

        rgGender.check(R.id.rb_editor_unisex);
        rgQuantityMode.check(R.id.rb_editor_do_naught);
    }


    private void saveFragrance() {

        int quantity;
        String fragranceName = etFragranceName.getText().toString().trim();
        int fragrancePrice = Integer.valueOf(etFragrancePrice.getText().toString());
        int setQuantity = Integer.valueOf(etQuantity.getText().toString());
        int genderInt = getGenderInt(rgGender.getCheckedRadioButtonId());

        FragranceEntry fragrance = null;
        if (initialQuantity == 0) {
            // Then fragrance is either new or an old without quantity values
            if (rbAdd.isChecked()) {
                quantity = setQuantity;
                fragrance =
                        new FragranceEntry(fragranceName, fragrancePrice, quantity, initialQuantitySold, genderInt);
            } else if (rbSell.isChecked()) {
                Toast.makeText(EditorActivity.this,
                        R.string.cannot_sell, Toast.LENGTH_SHORT).show();
                return;
            } else if (rbDoNaught.isChecked()) {
                fragrance =
                        new FragranceEntry(fragranceName, fragrancePrice, setQuantity, initialQuantitySold, genderInt);
            }
        } else if (initialQuantity > 0) {
            if (rbAdd.isChecked()) {
                quantity = initialQuantity + setQuantity;
                fragrance =
                        new FragranceEntry(fragranceName, fragrancePrice, quantity, initialQuantitySold, genderInt);
            } else if (rbSell.isChecked()) {
                quantity = initialQuantity - setQuantity;
                int newQuantitySold = initialQuantitySold + setQuantity;
                fragrance =
                        new FragranceEntry(fragranceName, fragrancePrice, quantity, newQuantitySold, genderInt);
            } else if (rbDoNaught.isChecked()) {
                fragrance =
                        new FragranceEntry(fragranceName, fragrancePrice, setQuantity, initialQuantitySold, genderInt);
            }
        }
        final FragranceEntry mFragrance = fragrance;
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mFragranceId == DEFAULT_FRAGRANCE_ID) {
                    //insert new task
                    mDb.mFragranceDao().insertFragrance(mFragrance);
                } else {
                    mFragrance.setId(mFragranceId);
                    mDb.mFragranceDao().updateFragrance(mFragrance);
                }
                finish();
            }
        });
    }

    private boolean validateFields() {

        if (TextUtils.isEmpty(etFragranceName.getText().toString())) {
            etFragranceName.setError("Required");
            return false;
        }
        if (TextUtils.isEmpty(etFragrancePrice.getText().toString())) {
            etFragrancePrice.setError("Required");
            return false;
        }
        if (Integer.valueOf(etFragrancePrice.getText().toString()) <= 0) {
            etFragrancePrice.setError("Invalid price");
            return false;
        }

        if (TextUtils.isEmpty(etQuantity.getText().toString())) {
            etQuantity.setError("Required");
            return false;
        }

        if (Integer.valueOf(etQuantity.getText().toString()) <= 0) {
            etQuantity.setError("Required");
            return false;
        }

        if (getGenderInt(rgGender.getCheckedRadioButtonId()) == -1){
            Toast.makeText(EditorActivity.this, "Please choose gender", Toast.LENGTH_LONG).show();
            return false;
        }

        if (rbSell.isChecked() && Integer.valueOf(etQuantity.getText().toString()) != 0) {
            if (initialQuantity < Integer.valueOf(etQuantity.getText().toString())) {
                Toast.makeText(EditorActivity.this, "The quantity you are trying to sell is more than the " +
                        "quantity you have in stock", Toast.LENGTH_LONG).show();
                return false;
            }
        } else if (rbSell.isChecked() && Integer.valueOf(etQuantity.getText().toString()) == 0) {
            Toast.makeText(EditorActivity.this, "You cannot sell nothing", Toast.LENGTH_LONG).show();
            return false;
        } else if (rbDoNaught.isChecked()) {
            return true;
        }

        etFragranceName.setError(null);
        etFragrancePrice.setError(null);
        etQuantity.setError(null);
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
        outState.putInt(INSTANCE_FRAGRANCE_ID, mFragranceId);
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
