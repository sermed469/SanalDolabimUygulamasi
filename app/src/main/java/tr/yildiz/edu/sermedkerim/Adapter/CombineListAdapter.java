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

import tr.yildiz.edu.sermedkerim.Classes.Combine;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderContract;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderDbHelper;
import tr.yildiz.edu.sermedkerim.R;
import tr.yildiz.edu.sermedkerim.ShowSavedCombineActivity;

public class CombineListAdapter extends RecyclerView.Adapter<CombineListAdapter.CombineListViewHolder>{

    ArrayList<Combine> combineArrayList;
    Context context;

    public CombineListAdapter(ArrayList<Combine> combineArrayList, Context context) {
        this.combineArrayList = combineArrayList;
        this.context = context;
    }

    public static class CombineListViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageButton imageButton;
        Button button;

        public CombineListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.textViewCombineNumber);
            this.imageButton = itemView.findViewById(R.id.imageButtonDeleteCombine);
            this.button = itemView.findViewById(R.id.buttonShowSavedCombine);
        }
    }

    @NonNull
    @Override
    public CombineListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.combine_item,parent,false);
        CombineListAdapter.CombineListViewHolder holder = new CombineListAdapter.CombineListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CombineListViewHolder holder, int position) {
        holder.textView.setText(combineArrayList.get(position).getNumber());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowSavedCombineActivity.class);
                intent.putExtra("CombinePosition",combineArrayList.get(position).getNumber());
                context.startActivity(intent);
            }
        });

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Kombini Sil");
                builder.setMessage("Kombini silmek istediğinize emin misiniz?");
                builder.setNegativeButton("Hayır", null);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
                        SQLiteDatabase db = dbHelper.getReadableDatabase();

                        String selection = FeedReaderContract.FeedEntryCombine.COLUMN_NAME_NUM + " LIKE ?";
                        String[] selectionArgs = { combineArrayList.get(position).getNumber() };
                        int deletedRows = db.delete(FeedReaderContract.FeedEntryCombine.TABLE_NAME, selection, selectionArgs);

                        combineArrayList.remove(position);
                        CombineListAdapter.super.notifyDataSetChanged();
                        Toast.makeText(context,"Kombin silindi",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return combineArrayList.size();
    }
}
