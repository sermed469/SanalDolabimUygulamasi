package tr.yildiz.edu.sermedkerim.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tr.yildiz.edu.sermedkerim.ClothesImagesActivity;
import tr.yildiz.edu.sermedkerim.Classes.Drawer;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderContract;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderDbHelper;
import tr.yildiz.edu.sermedkerim.R;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {

    ArrayList<Drawer> drawers;
    Context context;

    public static class DrawerViewHolder extends RecyclerView.ViewHolder {

        TextView drawerNumber;
        Button addClothes;
        ImageButton deleteDrawer;

        public DrawerViewHolder(View v){
            super(v);
            drawerNumber = v.findViewById(R.id.DrawerNumberTextView);
            addClothes = v.findViewById(R.id.AddClothesImage);
            deleteDrawer = v.findViewById(R.id.deleteDrawerButton);
        }
    }

    public DrawerAdapter(ArrayList<Drawer> drawers, Context context) {
        this.drawers = drawers;
        this.context = context;
    }

    @NonNull
    @Override
    public DrawerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item,parent,false);
        DrawerViewHolder holder = new DrawerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerViewHolder holder, int position) {

        holder.drawerNumber.setText(drawers.get(position).getNumber());

        holder.addClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClothesImagesActivity.class);
                intent.putExtra("drawerposition",position);
                context.startActivity(intent);
            }
        });

        holder.deleteDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Çekmece Sil");
                builder.setMessage("Çekmeceyi silmek istediğinize emin misiniz?");
                builder.setNegativeButton("Hayır", null);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
                        SQLiteDatabase db = dbHelper.getReadableDatabase();

                        String selection = FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_DRAWER + " LIKE ?";
                        String[] selectionArgs = { drawers.get(position).getNumber() };
                        int deletedRows = db.delete(FeedReaderContract.FeedEntryClothesPhoto.TABLE_NAME, selection, selectionArgs);

                        selection = FeedReaderContract.FeedEntryDrawer.COLUMN_NAME_NUM + " LIKE ?";

                        deletedRows = db.delete(FeedReaderContract.FeedEntryDrawer.TABLE_NAME, selection, selectionArgs);

                        drawers.remove(position);
                        DrawerAdapter.super.notifyDataSetChanged();
                        Toast.makeText(context,"çekmece silindi",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return drawers.size();
    }

}
