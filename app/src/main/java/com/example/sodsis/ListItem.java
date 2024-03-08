package com.example.sodsis;

public class ListItem {
    private String loc,name,type,area,temp,hum,rain,water, id;
    private int drip;
    private int imageResourceId;

    public ListItem(String loc,String name,String type,String area,String temp,String hum,String rain,String water,String id,int drip, int imageResourceId) {
        this.loc = loc;
        this.name=name;
        this.type=type;
        this.area=area;
        this.water=water;
        this.temp=temp;
        this.id=id;
        this.hum=hum;
        this.rain=rain;
        this.drip=drip;
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
    public String getId(){
        return id;
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
    public int getDrip() {
        return drip;
    }
    public void setDrip(int drip) {
        this.drip=drip;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
