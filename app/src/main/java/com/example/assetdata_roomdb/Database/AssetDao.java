package com.example.assetdata_roomdb.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AssetDao {

    @Insert
    void insert(AssetEntity asset);

    @Delete
    void delete(AssetEntity asset);

    @Query("SELECT * FROM asset_table")
    List<AssetEntity> getAllAssets();

    @Query("SELECT * FROM asset_table WHERE assetNumber=:assetNo")
    AssetEntity getAssetByNumber(String assetNo);

    @Query("SELECT COUNT(*) FROM asset_table WHERE assetNumber=:assetNo")
    int checkAssetExists(String assetNo);
}
