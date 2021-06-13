package tr.yildiz.edu.sermedkerim.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tr.yildiz.edu.sermedkerim.ActivityDetailsUpdate;
import tr.yildiz.edu.sermedkerim.Classes.Etkinlik;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderContract;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderDbHelper;
import tr.yildiz.edu.sermedkerim.R;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ActivityListViewHolder>{

    ArrayList<Etkinlik> etkinlikListesi;
    Context context;

    public ActivityListAdapter(ArrayList<Etkinlik> etkinlikListesi, Context context) {
        this.etkinlikListesi = etkinlikListesi;
        this.context = context;
    }

    public static class ActivityListViewHolder extends RecyclerView.ViewHolder{

        TextView name,date,location;
        ImageButton imageButton;
        ImageButton delete;

        public ActivityListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewRecyclerEtkinlikIsmi);
            date = itemView.findViewById(R.id.textViewRecylerActivityDate);
            location = itemView.findViewById(R.id.textViewRecyclerActivityLocation);
            imageButton = itemView.findViewById(R.id.imageButtonUpdateActivityDetails);
            delete = itemView.findViewById(R.id.imageButtonDeleteEtkinlik);
        }
    }

    @NonNull
    @Override
    public ActivityListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_activityinfo,parent,false);
        ActivityListAdapter.ActivityListViewHolder holder = new ActivityListAdapter.ActivityListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityListViewHolder holder, int position) {
        holder.name.setText(etkinlikListesi.get(position).getName());
        holder.date.setText(etkinlikListesi.get(position).getDate());
        holder.location.setText(etkinlikListesi.get(position).getLocation());

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityDetailsUpdate.class);
                intent.putExtra("updateposition",etkinlikListesi.get(position).getName());
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Etkinlik Sil");
                builder.setMessage("Etkinliği silmek istediğinize emin misiniz?");
                builder.setNegativeButton("Hayır", null);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
                        SQLiteDatabase db = dbHelper.getReadableDatabase();

                        String selection = FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_NAME + " LIKE ?";
                        String[] selectionArgs = { etkinlikListesi.get(position).getName() };
                        int deletedRows = db.delete(FeedReaderContract.FeedEntryEtkinlik.TABLE_NAME, selection, selectionArgs);

                        String selection1 = FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_ETKINLIK+ " LIKE ?";
                        String[] selectionArgs1 = { etkinlikListesi.get(position).getName() };
                        int deletedRows1 = db.delete(FeedReaderContract.FeedEntryEtkinlikPhoto.TABLE_NAME, selection1, selectionArgs1);

                        etkinlikListesi.remove(position);
                        ActivityListAdapter.super.notifyDataSetChanged();
                        Toast.makeText(context,"Etkinlik silindi",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return etkinlikListesi.size();
    }
}
