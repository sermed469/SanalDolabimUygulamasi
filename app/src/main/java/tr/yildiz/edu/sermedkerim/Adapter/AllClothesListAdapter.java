package tr.yildiz.edu.sermedkerim.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tr.yildiz.edu.sermedkerim.Classes.Clothes;
import tr.yildiz.edu.sermedkerim.R;

import static android.content.Context.MODE_PRIVATE;

public class AllClothesListAdapter extends RecyclerView.Adapter<AllClothesListAdapter.AllClothesListViewHolder> {

    ArrayList<Clothes> clothes;
    Context context;

    public AllClothesListAdapter(ArrayList<Clothes> clothes, Context context) {
        this.clothes = clothes;
        this.context = context;
    }

    public static class AllClothesListViewHolder extends RecyclerView.ViewHolder{

        ImageView photo;
        TextView type,color,desen,price,date;
        CheckBox checkBox;

        public AllClothesListViewHolder(View v){
            super(v);
            photo = v.findViewById(R.id.imageViewAllClothesList);
            type = v.findViewById(R.id.textViewAllClothesListType);
            color = v.findViewById(R.id.textViewAllClothesListColor);
            desen = v.findViewById(R.id.textViewAllClothesListDesen);
            date = v.findViewById(R.id.textViewAllClothesListDate);
            price = v.findViewById(R.id.textViewAllClothesListPrice);
            checkBox = v.findViewById(R.id.checkBoxChooseClothes);
        }
    }

    @NonNull
    @Override
    public AllClothesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_clothes,parent,false);
        AllClothesListAdapter.AllClothesListViewHolder holder = new AllClothesListAdapter.AllClothesListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllClothesListViewHolder holder, int position) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("Shared",MODE_PRIVATE);
        int control = sharedPreferences.getInt("clotheslist",0);

        holder.photo.setImageBitmap(clothes.get(position).getPhoto());
        holder.type.setText(clothes.get(position).getClothesType());
        holder.color.setText(clothes.get(position).getColor());
        holder.desen.setText(clothes.get(position).getFigure());
        holder.date.setText(clothes.get(position).getDate());
        holder.price.setText(clothes.get(position).getPrice().toString());

        if(control == 0){
            holder.checkBox.setVisibility(View.INVISIBLE);
        }
        else{
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        Clothes.selectedClothes.add(clothes.get(position));
                    }
                    else{
                        Clothes.selectedClothes.remove(clothes.get(position));
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }

}
