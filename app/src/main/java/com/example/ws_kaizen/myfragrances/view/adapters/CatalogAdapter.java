package com.example.ws_kaizen.myfragrances.view.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ws_kaizen.myfragrances.R;
import com.example.ws_kaizen.myfragrances.database.AppDatabase;
import com.example.ws_kaizen.myfragrances.database.FragranceEntry;
import com.example.ws_kaizen.myfragrances.utilities.AppExecutors;
import com.example.ws_kaizen.myfragrances.view.activities.CatalogActivity;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class CatalogAdapter
        extends RecyclerView.Adapter<CatalogAdapter.FragrancesViewHolder>
        implements Filterable {

    private static final String TAG = "CatalogAdapter";

    private Context mContext;
    List<FragranceEntry> unfilteredList;
    List<FragranceEntry> filteredList;
    private final LayoutInflater mInflater;
    CustomFilter filter = new CustomFilter();
    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;


    public CatalogAdapter(@NonNull Context context, ItemClickListener listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mItemClickListener = listener;
    }

    public void setFragrances(List<FragranceEntry> fragrances) {
        this.filteredList = fragrances;
        this.unfilteredList = fragrances;
        notifyDataSetChanged();
    }

    public void clearData() {
        this.filteredList = new ArrayList<>();
        this.unfilteredList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        if (filteredList != null)
            return filteredList.size();
        else
            return 0;
    }

    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @NonNull
    @Override
    public CatalogAdapter.FragrancesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater
                .inflate(R.layout.item_fragrance, parent, false);
        return new FragrancesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FragrancesViewHolder holder, int position) {
        FragranceEntry fragrance = filteredList.get(position);

    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    public class FragrancesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {


        public FragrancesViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onClick(View v) {


        }


    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    public class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    }


}

