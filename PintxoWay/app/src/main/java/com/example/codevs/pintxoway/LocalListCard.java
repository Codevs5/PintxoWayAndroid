package com.example.codevs.pintxoway;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.type;

/**
 * Created by Borja on 25/01/2017.
 */

public class LocalListCard {

    private String placeID,name,vicinity,icon,distance,type,place_level,photoID,photoH,photoW;
    private boolean openNow;


    public LocalListCard(JSONObject local){
        //TODO creqar a partir de Json la tarjeta
        try {
            placeID = local.getString("placeId");
            name = local.getString("name");
            vicinity = local.getString("vicinity");
            icon = "";//local.getString("icon");
            distance =" - 400 m";// + local.getString("distance") + " m";
            if(Math.random() < 0.5){
                type="bar";
            }else{
                type = "restaurante";//local.getString("type");
            }
            place_level ="0";// local.getString("priceLvl");
            //falta la foto

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlace_level() {
        return place_level;
    }

    public void setPlace_level(String place_level) {
        this.place_level = place_level;
    }

    public String getPhotoID() {
        return photoID;
    }

    public void setPhotoID(String photoID) {
        this.photoID = photoID;
    }

    public String getPhotoH() {
        return photoH;
    }

    public void setPhotoH(String photoH) {
        this.photoH = photoH;
    }

    public String getPhotoW() {
        return photoW;
    }

    public void setPhotoW(String photoW) {
        this.photoW = photoW;
    }

    public boolean isOpenNow() {
        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
