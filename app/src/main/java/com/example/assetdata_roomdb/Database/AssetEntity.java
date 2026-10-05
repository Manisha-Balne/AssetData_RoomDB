package com.example.assetdata_roomdb.Database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "asset_table")
public class AssetEntity {

    @PrimaryKey
    @NonNull
    private String assetNumber;

    private String description;
    private String category;
    private String location;

    public AssetEntity(@NonNull String assetNumber,
                       String description,
                       String category,
                       String location) {

        this.assetNumber = assetNumber;
        this.description = description;
        this.category = category;
        this.location = location;
    }

    @NonNull
    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(@NonNull String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
