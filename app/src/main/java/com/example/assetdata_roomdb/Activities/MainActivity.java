package com.example.assetdata_roomdb.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assetdata_roomdb.Adapter.AssetAdapter;
import com.example.assetdata_roomdb.Database.AssetDatabase;
import com.example.assetdata_roomdb.Database.AssetEntity;
import com.example.assetdata_roomdb.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private Button btnAddAsset;
    private RecyclerView recyclerView;
    private AssetDatabase database;
    private AssetAdapter adapter;
    private List<AssetEntity> assetList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnAddAsset = findViewById(R.id.btnAddAsset);
        recyclerView = findViewById(R.id.recyclerView);
        database = AssetDatabase.getInstance(this);

        btnAddAsset.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddAssetActivity.class);
            startActivity(intent);
        });
        executors();

    }
    @Override
    protected void onResume() {
        super.onResume();
        executors();
    }
    public void executors(){
        Executors.newSingleThreadExecutor().execute(() -> {
            assetList = database.assetDao().getAllAssets();
            runOnUiThread(() -> {
                adapter = new AssetAdapter(MainActivity.this, assetList);
                recyclerView.setLayoutManager(new GridLayoutManager(this,2));
                recyclerView.setAdapter(adapter);
            });
        });
    }
}