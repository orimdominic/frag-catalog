package com.example.ws_kaizen.myfragrances.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ws_kaizen.myfragrances.R;
import com.example.ws_kaizen.myfragrances.database.FragranceEntry;

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

        holder.tvNumber.setText(String.valueOf(position + 1));
        holder.tvName.setText(fragrance.getName());
        holder.tvRetPrice.setText(mContext.getString(R.string.prefix_ret) + String.valueOf(fragrance.getRet_price()));
        holder.tvWsPrice.setText(mContext.getString(R.string.prefix_ws) + String.valueOf(fragrance.getWs_price()));
        if (fragrance.getQuantityInStock() == 1) {
            holder.tvQuantityInStock.setText(String.valueOf(fragrance.getQuantityInStock()) + " piece in stock");
        } else {
            holder.tvQuantityInStock.setText(String.valueOf(fragrance.getQuantityInStock()) + " pieces in stock");
        }
        if (fragrance.getQuantitySold() == 1) {
            holder.tvQuantitySold.setText(String.valueOf(fragrance.getQuantitySold()) + " piece sold");
        } else {
            holder.tvQuantitySold.setText(String.valueOf(fragrance.getQuantitySold()) + " pieces sold");
        }
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    public class FragrancesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        RelativeLayout rlFragranceItem;
        TextView tvNumber;
        TextView tvName;
        TextView tvRetPrice;
        TextView tvWsPrice;
        TextView tvQuantityInStock;
        TextView tvQuantitySold;

        public FragrancesViewHolder(View itemView) {
            super(itemView);
            rlFragranceItem = itemView.findViewById(R.id.rl_fragranceItem);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvName = itemView.findViewById(R.id.tv_fragrance_name);
            tvRetPrice = itemView.findViewById(R.id.tv_fragrance_ret_price);
            tvWsPrice = itemView.findViewById(R.id.tv_fragrance_ws_price);
            tvQuantityInStock = itemView.findViewById(R.id.tv_quantity_in_stock);
            tvQuantitySold = itemView.findViewById(R.id.tv_quantity_sold);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int elementId = filteredList.get(getAdapterPosition()).getId();
            Log.d(TAG, "onClick: " + elementId);
            mItemClickListener.onItemClickListener(elementId);

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
            final List<FragranceEntry> list = unfilteredList;
            int count = list.size();
            final List<FragranceEntry> nlist = new ArrayList<>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getName().toLowerCase();
                if (filterableString.contains(filterString.toLowerCase())) {
                    nlist.add(list.get(i));
                }
            }
            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (List<FragranceEntry>) results.values;
            notifyDataSetChanged();
        }
    }


}

