package com.example.sodsis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {

    private List<String> originalData;
    private List<String> filteredData;
    private final Object lock = new Object();

    // Constructor
    public MyAdapter(List<String> data) {
        this.originalData = data;
        this.filteredData = new ArrayList<>(data);
    }

    // ViewHolder sınıfı
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    // onCreateViewHolder() metodu
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    // onBindViewHolder() metodu
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(filteredData.get(position));
    }

    // getItemCount() metodu
    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    // getFilter() metodu
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                synchronized (lock) {
                    String charString = constraint.toString();
                    if (charString.isEmpty()) {
                        filteredData = new ArrayList<>(originalData);
                    } else {
                        List<String> filteredList = new ArrayList<>();
                        for (String item : originalData) {
                            if (item.toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(item);
                            }
                        }
                        filteredData = filteredList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filteredData;
                    return filterResults;
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (List<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
