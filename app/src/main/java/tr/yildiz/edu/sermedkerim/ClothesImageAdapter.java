package tr.yildiz.edu.sermedkerim;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClothesImageAdapter extends RecyclerView.Adapter<ClothesImageAdapter.ClothesImageViewHolder> {

    ArrayList<Bitmap> clothesİmages;
    Context context;
    static final int REQUEST_IMAGE_OPEN = 100;
    Uri URI = null;

    public static class ClothesImageViewHolder extends RecyclerView.ViewHolder {

        ImageView clothesImage;
        ImageButton delete,update;

        public ClothesImageViewHolder(View v){
            super(v);
            clothesImage = v.findViewById(R.id.imageViewClothesImage);
            delete = v.findViewById(R.id.imageButtonDelete);
            update = v.findViewById(R.id.imageButtonUpdate);
        }
    }

    public ClothesImageAdapter(ArrayList<Bitmap> clothesİmages, Context context) {
        this.clothesİmages = clothesİmages;
        this.context = context;
    }

    @NonNull
    @Override
    public ClothesImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothes_image,parent,false);
        ClothesImageAdapter.ClothesImageViewHolder holder = new ClothesImageAdapter.ClothesImageViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClothesImageViewHolder holder, int position) {
        holder.clothesImage.setImageBitmap(clothesİmages.get(position));

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = context.getSharedPreferences("Shared",context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("update",1);
                editor.putInt("position",position);
                editor.apply();

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                ((Activity)context).startActivityForResult(intent, REQUEST_IMAGE_OPEN);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Kıyafet Sil");
                builder.setMessage("Kıyafet resmini silmek istediğinize emin misiniz?");
                builder.setNegativeButton("Hayır", null);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clothesİmages.remove(position);
                        ClothesImageAdapter.super.notifyDataSetChanged();
                        Toast.makeText(context,"kıyafet silindi",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        holder.clothesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getintent = ((Activity) context).getIntent();
                int drawerposition = getintent.getIntExtra("drawerposition",0);

                Intent intent = new Intent(context,ClothesActivity.class);
                intent.putExtra("DrawerPosition",drawerposition);
                intent.putExtra("imageposition",position);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return clothesİmages.size();
    }


}
