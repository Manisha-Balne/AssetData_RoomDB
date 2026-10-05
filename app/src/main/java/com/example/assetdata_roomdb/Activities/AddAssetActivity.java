package com.example.assetdata_roomdb.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assetdata_roomdb.Database.AssetDatabase;
import com.example.assetdata_roomdb.Database.AssetEntity;
import com.example.assetdata_roomdb.R;

import java.util.concurrent.Executors;

public class AddAssetActivity extends AppCompatActivity {
    EditText etAssetNumber,etDescription,etCategory,etLocation;
    Button btnSave,btnCancel;
    AssetDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_asset);

        etAssetNumber = findViewById(R.id.etAssetNumber);
        etDescription = findViewById(R.id.etDescription);
        etCategory = findViewById(R.id.etCategory);
        etLocation = findViewById(R.id.etLocation);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        db = AssetDatabase.getInstance(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String assetNumber = etAssetNumber.getText().toString();
                String description = etDescription.getText().toString();
                String category = etCategory.getText().toString();
                String location = etLocation.getText().toString();

                if (assetNumber.isEmpty()) {
                    etAssetNumber.setError(getString(R.string.etAssetno_empty));
                    return;
                }

                if (description.isEmpty()) {
                    etDescription.setError(getString(R.string.etAssetdes_empty));
                    return;
                }

                if (category.isEmpty()) {
                    etCategory.setError(getString(R.string.etAssetcat_empty));
                    return;
                }

                if (location.isEmpty()) {
                    etLocation.setError(getString(R.string.etAssetlocation_empty));
                    return;
                }

                Executors.newSingleThreadExecutor().execute(() -> {

                    int count = db.assetDao().checkAssetExists(assetNumber);

                    if (count > 0) {
                        runOnUiThread(() ->
                                Toast.makeText(AddAssetActivity.this, "Asset Number already exists", Toast.LENGTH_SHORT).show());
                        return;
                    }

                    AssetEntity asset = new AssetEntity(assetNumber, description, category, location);
                    db.assetDao().insert(asset);
                    runOnUiThread(() -> {
                        Toast.makeText(AddAssetActivity.this, "Asset added successfully", Toast.LENGTH_SHORT).show();
                                finish();
                    });
                });

            }
        });
        btnCancel.setOnClickListener(v -> {
            finish();
        });
    }
}