package tr.yildiz.edu.sermedkerim;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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

public class CombineListAdapter extends RecyclerView.Adapter<CombineListAdapter.CombineListViewHolder>{

    ArrayList<Bitmap[]> combineArrayList;
    Context context;

    public CombineListAdapter(ArrayList<Bitmap[]> combineArrayList, Context context) {
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
        holder.textView.setText((position + 1) + ". Kombin");

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ShowSavedCombineActivity.class);
                intent.putExtra("CombinePosition",position);
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
