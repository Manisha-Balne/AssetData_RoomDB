package com.example.assetdata_roomdb.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AssetEntity.class}, version = 1, exportSchema = false)
public abstract class AssetDatabase extends RoomDatabase {

    public abstract AssetDao assetDao();

    private static AssetDatabase INSTANCE;

    public static synchronized AssetDatabase getInstance(Context context){

        if(INSTANCE == null){

            INSTANCE = Room.databaseBuilder(context, AssetDatabase.class, "asset_db")
                            .fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
