package com.example.sodsis;

import java.util.List;

public class CityListResponse {
    private List<City> list;

    public List<City> getCityList() {
        return list;
    }

    public static class City {
        private String cityName;

        public String getCityName() {
            return cityName;
        }
    }
}