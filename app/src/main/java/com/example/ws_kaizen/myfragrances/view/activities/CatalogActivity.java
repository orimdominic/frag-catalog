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

    //    private RecyclerView rvFragrances;
    private FragranceAdapter lvAdapter;
    private ListView lvFragrances;
    private FloatingActionButton fabAddFragrance;

    private AppDatabase mDb;
    private List<FragranceEntry> loadedFragranceEntries;
    private boolean isSelectedAll;
    private ArrayList<FragranceEntry> selectedFragrances;
    private ArrayList<Integer> fragrancesToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_catalog);
        instantiateViews();
        mDb = AppDatabase.getInstance(getApplicationContext());
        setupViewModel();

        isSelectedAll = false;
        selectedFragrances = new ArrayList<>();
    }

    private void setupViewModel() {
        CatalogViewModel viewModel = ViewModelProviders.of(this).get(CatalogViewModel.class);
        viewModel.getFragrances().observe(this, new Observer<List<FragranceEntry>>() {
            @Override
            public void onChanged(@Nullable List<FragranceEntry> fragranceEntries) {
                if (fragranceEntries != null) {
                    loadedFragranceEntries = fragranceEntries;
                    lvAdapter = new FragranceAdapter(CatalogActivity.this, loadedFragranceEntries);
                    lvFragrances.setAdapter(lvAdapter);
                    lvAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void instantiateViews() {
        lvFragrances = findViewById(R.id.lv_fragrance);


        lvFragrances.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragranceEntry fragranceToEdit = lvAdapter.getItem(position);
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                intent.putExtra(EditorActivity.EXTRA_FRAGRANCE_ID, fragranceToEdit.getId());
                startActivity(intent);
            }
        });
        lvFragrances.setTextFilterEnabled(true);
        lvFragrances.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lvFragrances.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, final boolean checked) {
                if (checked) {
                    selectedFragrances.add(loadedFragranceEntries.get(position));
                    String modeTitle = getResources().getQuantityString(R.plurals.plural_name, selectedFragrances.size(), selectedFragrances.size());
                    mode.setTitle(modeTitle + " selected");
                } else {
                    selectedFragrances.remove(loadedFragranceEntries.get(position));
                    String modeTitle = getResources().getQuantityString(R.plurals.plural_name, selectedFragrances.size(), selectedFragrances.size());
                    mode.setTitle(modeTitle + " selected");
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {

                mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.select_all:
                        toggleSelectAllMenuItem(mode);
                        break;
                    case R.id.delete:
                        showDeleteConfirmationDialog(mode);
                        break;
                    case R.id.share:
                        requestShareFormat(mode);
//                        createSharableText(mode);
                        break;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                selectedFragrances.clear();
            }
        });


        fabAddFragrance = findViewById(R.id.fab_add_fragrance);
        fabAddFragrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toggleSelectAllMenuItem(ActionMode mode) {
        isSelectedAll = !isSelectedAll;
        selectedFragrances.clear();
        if (isSelectedAll) {

            for (int i = 0; i < loadedFragranceEntries.size(); i++) {
                lvFragrances.setItemChecked(i, true);
            }
            String modeTitle =
                    getResources()
                            .getQuantityString(R.plurals.plural_name,
                                    selectedFragrances.size(),
                                    selectedFragrances.size());
            mode.setTitle(modeTitle + " selected");
        } else {
            for (int i = 0; i < loadedFragranceEntries.size(); i++) {
                lvFragrances.setItemChecked(i, false);
            }
        }
    }

    private void showDeleteConfirmationDialog(final ActionMode mode) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        String message = getResources().
                getQuantityString(R.plurals.plural_name,
                        selectedFragrances.size(), selectedFragrances.size());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete " + message + "?");
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the fragrance.
                deleteFragrances(mode);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the fragrance.
                if (dialog != null) {
                    selectedFragrances.clear();
                    setupViewModel();
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void deleteFragrances(ActionMode mode) {
//        Create an array of integers of the ids of the fragrances
//        Pass the array to the delete method of the dao
        fragrancesToDelete = new ArrayList<>();
        for (FragranceEntry fragranceToDelete : selectedFragrances) {
            fragrancesToDelete.add(fragranceToDelete.getId());
        }

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.mFragranceDao().deleteFragranceById(fragrancesToDelete);
            }
        });
        mode.finish();
        selectedFragrances.clear();
        lvAdapter.notifyDataSetChanged();
        String message = getResources()
                .getQuantityString(R.plurals.plural_name, fragrancesToDelete.size(), fragrancesToDelete.size());
        Toast.makeText(CatalogActivity.this, message + " deleted", Toast.LENGTH_SHORT).show();
        fragrancesToDelete.clear();
    }

    private void requestShareFormat(final ActionMode mode) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Include fragrance gender?");
        builder.setPositiveButton("Include", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                createSharableText(mode, true);
            }
        });
        builder.setNegativeButton("Don't include", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                createSharableText(mode, false);
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void createSharableText(ActionMode mode, boolean includeGender) {
        final StringBuilder textToShare = new StringBuilder();
        if (includeGender) {
            for (FragranceEntry fragrance : selectedFragrances) {
                textToShare.append(fragrance.getName() + "\n");
                textToShare.append(fragrance.getGenderString());
                textToShare.append(",   ");
                textToShare.append(fragrance.getRet_price() + "\n");
                textToShare.append("\n");
            }
            Log.d(TAG, "createSharableText: "+textToShare.toString());

        } else {
//            Log.d(TAG, "createSharableText: notInclucingGender");
            for (FragranceEntry fragrance : selectedFragrances) {
                textToShare.append(fragrance.getName() + "\n");
                textToShare.append(fragrance.getRet_price() + "\n");
                textToShare.append("\n");
            }
            Log.d(TAG, "createSharableText: "+textToShare.toString());
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare.toString());
        shareIntent.setType("text/plain");

        // Verify that the intent will resolve to an activity
        if (shareIntent.resolveActivity(CatalogActivity.this.getPackageManager()) != null) {
            CatalogActivity.this.startActivity(shareIntent);
        }
        selectedFragrances.clear();
        mode.finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lvAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }


    // DONE: 1. Set onClickListener on RV item to move to editor activity
    // DONE: 2. Set onLongClickListener on RV item to select multiple
    // DONE: 3. Open menu/replace it to show share icon and delete icon on selected multiple
    // DONE: 4. Set click on share icon to open intent for sharing fragrances names and price lists threough whatsapp or others
    // DONE: 5. Set click on delete icon to delete frag(s) selected
    // DONE: 6. Add numbering to recyclerview
    // DONE: 7. Change theme
    // TODO: 8. Add preferences for arranging fragrances

}
