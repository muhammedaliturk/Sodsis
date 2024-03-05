package com.example.sodsis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class WeatherAdapter extends BaseAdapter {
    private Context context;
    private List<CityListResponse.City> cities;

    public WeatherAdapter(Context context, List<CityListResponse.City> cities) {
        this.context = context;
        this.cities = cities;
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        CityListResponse.City city = cities.get(position);
        TextView cityNameTextView = convertView.findViewById(android.R.id.text1);
        cityNameTextView.setText(city.getCityName());

        return convertView;
    }
}
