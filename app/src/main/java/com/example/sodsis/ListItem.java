package com.example.sodsis;

public class ListItem {
    private String loc,name,type,area,temp,hum,rain,water;
    private int imageResourceId;

    public ListItem(String loc,String name,String type,String area,String temp,String hum,String rain,String water, int imageResourceId) {
        this.loc = loc;
        this.name=name;
        this.type=type;
        this.area=area;
        this.water=water;
        this.temp=temp;
        this.hum=hum;
        this.rain=rain;
        this.imageResourceId = imageResourceId;
    }

    public String getLoc() {
        return loc;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public String getArea() {
        return area;
    }
    public String getTemp() {
        return temp;
    }
    public String getHum() {
        return hum;
    }
    public String getRain() {
        return rain;
    }
    public String getWater() {
        return water;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
