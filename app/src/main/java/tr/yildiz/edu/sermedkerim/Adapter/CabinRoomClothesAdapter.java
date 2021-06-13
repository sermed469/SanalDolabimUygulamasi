package tr.yildiz.edu.sermedkerim.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tr.yildiz.edu.sermedkerim.CabinRoomActivity;
import tr.yildiz.edu.sermedkerim.Classes.Clothes;
import tr.yildiz.edu.sermedkerim.R;

public class CabinRoomClothesAdapter extends RecyclerView.Adapter<CabinRoomClothesAdapter.CabinRoomClothesViewHolder>{

    ArrayList<Clothes> clothesPhoto;
    Context context;

    public CabinRoomClothesAdapter(ArrayList<Clothes> clothesPhoto, Context context) {
        this.clothesPhoto = clothesPhoto;
        this.context = context;
    }

    public static class CabinRoomClothesViewHolder extends RecyclerView.ViewHolder{

        ImageView photo;
        ImageButton select;

        public CabinRoomClothesViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.imageViewCabinRoomSelectedClothes);
            select = itemView.findViewById(R.id.imageButtonSelectCombineClothes);
        }
    }

    @NonNull
    @Override
    public CabinRoomClothesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cabinroom_clothes,parent,false);
        CabinRoomClothesAdapter.CabinRoomClothesViewHolder holder = new CabinRoomClothesAdapter.CabinRoomClothesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CabinRoomClothesViewHolder holder, int position) {
        holder.photo.setImageBitmap(clothesPhoto.get(position).getPhoto());

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CabinRoomActivity.class);
                //Clothes.cabinRoomClothes.add(clothesPhoto.get(position).getPhoto());

                switch (clothesPhoto.get(position).getClothesType()){
                    case "Şapka":
                        Clothes.combine.put("head",clothesPhoto.get(position));
                        Clothes.combine.put("face",clothesPhoto.get(position));
                        System.out.println("aaaaaaaaaaaaaaaa");
                        break;
                    case "Yüz":
                        Clothes.combine.put("face",clothesPhoto.get(position));
                        break;
                    case "T-Shirt":
                        Clothes.combine.put("ustbeden",clothesPhoto.get(position));
                        break;
                    case "Gömlek":
                        Clothes.combine.put("ustbeden",clothesPhoto.get(position));
                        break;
                    case "Pantalon":
                        Clothes.combine.put("altbeden",clothesPhoto.get(position));
                        break;
                    case "Şort":
                        Clothes.combine.put("altbeden",clothesPhoto.get(position));
                        break;
                    case "Ayakkabı":
                        Clothes.combine.put("ayak",clothesPhoto.get(position));
                        break;
                }

                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return clothesPhoto.size();
    }
}
