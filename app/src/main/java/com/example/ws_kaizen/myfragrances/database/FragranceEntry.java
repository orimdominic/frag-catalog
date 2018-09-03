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
    @NonNull
    private int id;
    private String name;
    private int price;
    @ColumnInfo(name = "quantity_in_stock")
    private int quantityInStock;
    @ColumnInfo(name = "quantity_sold")
    private int quantitySold;
    private int gender; // 1 for male, 2 for female, 3 for unisex


    @Ignore
    public FragranceEntry(String name, int price, int quantityInStock, int quantitySold, int gender) {
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.quantitySold = quantitySold;
        this.gender = gender;
    }

    public FragranceEntry(int id, String name, int price, int quantityInStock, int quantitySold, int gender) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.quantitySold = quantitySold;
        this.gender = gender;

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getGenderString() {
        String genderString;
        switch (gender) {
            case 1:
                genderString = "Male";
                break;
            case 2:
                genderString = "Female";
                break;
            default:
                genderString = "Unisex";
                break;
        }
        return genderString;
    }

}
