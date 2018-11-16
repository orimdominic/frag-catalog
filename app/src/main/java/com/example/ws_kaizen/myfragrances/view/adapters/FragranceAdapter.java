package com.example.ws_kaizen.myfragrances.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.ws_kaizen.myfragrances.R;
import com.example.ws_kaizen.myfragrances.database.FragranceEntry;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class FragranceAdapter extends ArrayAdapter<FragranceEntry> implements Filterable {

    private Context context;

    CustomFilter filter = new CustomFilter();


    public FragranceAdapter(@NonNull Context context, List<FragranceEntry> allFragrances) {
        super(context, 0);
        this.context = context;

    }

    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View rowView, @NonNull ViewGroup parent) {

        rowView = LayoutInflater.from(context)
                .inflate(R.layout.item_fragrance, parent, false);


        return rowView;
    }


    public class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    }

}
