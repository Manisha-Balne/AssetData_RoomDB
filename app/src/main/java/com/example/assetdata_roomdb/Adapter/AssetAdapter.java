package com.example.assetdata_roomdb.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assetdata_roomdb.Activities.AssetDetailsActivity;
import com.example.assetdata_roomdb.Database.AssetDatabase;
import com.example.assetdata_roomdb.Database.AssetEntity;
import com.example.assetdata_roomdb.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AssetAdapter extends RecyclerView.Adapter<AssetAdapter.ViewHolder>{

    private Context context;
    private List<AssetEntity> assetList;
    private AssetDatabase database;
    private final ExecutorService databaseExecutor;

    public AssetAdapter(Context context, List<AssetEntity> assetList){
        this.context = context;
        this.assetList = assetList;
        this.database = AssetDatabase.getInstance(context);
        this.databaseExecutor =  Executors.newSingleThreadExecutor();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.asset_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AssetEntity asset = assetList.get(position);

        holder.txtAssetNo.setText(asset.getAssetNumber());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AssetDetailsActivity.class);
            intent.putExtra("ASSET_NO", asset.getAssetNumber());
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            deleteConfirmationDialog(asset);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return assetList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtAssetNo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAssetNo = itemView.findViewById(R.id.txtAssetNumber);
        }
    }

    public void deleteConfirmationDialog(AssetEntity asset){
        new MaterialAlertDialogBuilder(context).setTitle("Delete Asset?")
                .setMessage("Are you sure you want to delete '" + asset.getAssetNumber() + "'?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                database.assetDao().delete(asset);
                                assetList.remove(asset);
                                if (context instanceof AppCompatActivity) {
                                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            notifyDataSetChanged();
                                            Toast.makeText(context, "Asset deleted successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null).show();
    }
}
