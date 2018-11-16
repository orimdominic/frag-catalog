package com.example.ws_kaizen.myfragrances.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "fragrance")
public class FragranceEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int eu_price;   // end user price (retail price)
    private int ws_price;   // wholesale price
    @ColumnInfo(name = "quantity_in_stock")
    private int quantityInStock;
    private int gender; // 1 for male, 2 for female, 3 for unisex

    //  Used by Android
    public FragranceEntry(String name, int eu_price, int ws_price, int quantityInStock, int gender) {
        this.name = name;
        this.eu_price = eu_price;
        this.ws_price = ws_price;
        this.quantityInStock = quantityInStock;
        this.gender = gender;
    }

    //  Used by Room
    public FragranceEntry( int id, String name, int eu_price, int ws_price, int quantityInStock, int gender) {
        this.id = id;
        this.name = name;
        this.eu_price = eu_price;
        this.ws_price = ws_price;
        this.quantityInStock = quantityInStock;
        this.gender = gender;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getEu_price() {
        return eu_price;
    }

    public int getWs_price() {
        return ws_price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public int getGender() {
        return gender;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEu_price(int eu_price) {
        this.eu_price = eu_price;
    }

    public void setWs_price(int ws_price) {
        this.ws_price = ws_price;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
