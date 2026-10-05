package com.example.assetdata_roomdb.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assetdata_roomdb.Database.AssetDatabase;
import com.example.assetdata_roomdb.Database.AssetEntity;
import com.example.assetdata_roomdb.R;

import java.util.concurrent.Executors;

public class AssetDetailsActivity extends AppCompatActivity {

    TextView txtAssetNumber,txtDescription,txtCategory,txtLocation;
    AssetDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_asset_details);

        txtAssetNumber = findViewById(R.id.txtAssetNumber);
        txtDescription = findViewById(R.id.txtDescription);
        txtCategory = findViewById(R.id.txtCategory);
        txtLocation = findViewById(R.id.txtLocation);

        db = AssetDatabase.getInstance(this);

        String assetNo = getIntent().getStringExtra("ASSET_NO");
        loadAssetDetails(assetNo);
    }

    private void loadAssetDetails(String assetNo) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AssetEntity asset = db.assetDao().getAssetByNumber(assetNo);
            runOnUiThread(() -> {
                if(asset != null) {
                    txtAssetNumber.setText(getString(R.string.Assetnumber) + asset.getAssetNumber());
                    txtDescription.setText(getString(R.string.AssetDescription) + asset.getDescription());
                    txtCategory.setText(getString(R.string.AssetCategory) + asset.getCategory());
                    txtLocation.setText(getString(R.string.AssetLocation) + asset.getLocation());
                }

            });

        });

    }
}