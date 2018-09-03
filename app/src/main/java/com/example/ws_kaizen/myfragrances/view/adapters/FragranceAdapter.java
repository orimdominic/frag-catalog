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
    private List<FragranceEntry> unfilteredList;
    private List<FragranceEntry> filteredList;
    CustomFilter filter = new CustomFilter();


    public FragranceAdapter(@NonNull Context context, List<FragranceEntry> allFragrances) {
        super(context, 0);
        this.context = context;
        this.filteredList = allFragrances;
        this.unfilteredList = allFragrances;
    }


    //For this helper method, return based on filteredData
    public int getCount() {
        if (filteredList != null)
            return filteredList.size();
        else
            return 0;
    }

    //This should return a data object, not an int
    public FragranceEntry getItem(int position) {
        return filteredList.get(position);
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

        //Get data from filteredList
        //This line can be replaced with:
        //     FragranceEntry fragrance = getItem(position);
        FragranceEntry fragrance = filteredList.get(position);

        TextView tvNumber = rowView.findViewById(R.id.tv_number);
        TextView tvName = rowView.findViewById(R.id.tv_fragrance_name);
        TextView tvPrice = rowView.findViewById(R.id.tv_fragrance_price);
        TextView tvQuantityInStock = rowView.findViewById(R.id.tv_quantity_in_stock);
        TextView tvQuantitySold = rowView.findViewById(R.id.tv_quantity_sold);
        TextView tvGender = rowView.findViewById(R.id.tv_fragrance_gender);

        tvNumber.setText(String.valueOf(position + 1));
        tvName.setText(fragrance.getName());

        tvPrice.setText(String.valueOf(fragrance.getPrice()));
        if (fragrance.getQuantityInStock() == 1) {
            tvQuantityInStock.setText(String.valueOf(fragrance.getQuantityInStock()) + " piece in stock");
        } else {
            tvQuantityInStock.setText(String.valueOf(fragrance.getQuantityInStock()) + " pieces in stock");
        }
        if (fragrance.getQuantitySold() == 1) {
            tvQuantitySold.setText(String.valueOf(fragrance.getQuantitySold()) + " piece sold");
        } else {
            tvQuantitySold.setText(String.valueOf(fragrance.getQuantitySold()) + " pieces sold");
        }
        tvGender.setText(fragrance.getGenderString()+",");

        return rowView;
    }


    public class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            //If there's nothing to filter on, return the original data for your list
            if (constraint == null || constraint.length() == 0) {
                results.values = unfilteredList;
                results.count = unfilteredList.size();
                return results;
            }

            String filterString = constraint.toString().toLowerCase();
            List<FragranceEntry> filteredResults = new ArrayList<>();

            if (filterString.length() > 0 && unfilteredList.size() > 0) {
                for (int i = 0; i < unfilteredList.size(); i++) {
                    String fragranceName = unfilteredList.get(i).getName().toLowerCase();
                    if (fragranceName.contains(filterString)) {
                        filteredResults.add(unfilteredList.get(i));
                    }
                }
                // set the Filtered result to return
                results.count = filteredResults.size();
                results.values = filteredResults;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (List<FragranceEntry>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

}
