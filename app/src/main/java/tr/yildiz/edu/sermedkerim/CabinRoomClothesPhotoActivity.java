package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Switch;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CabinRoomClothesPhotoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Clothes> commbinClothes = new ArrayList<>();
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabin_room_clothes_photo);

        Intent intent = getIntent();
        type = intent.getStringExtra("kabinbolum");

        if(type != null){
            switch (type){
                case "head":
                    filterPhotos("Şapka");
                    break;
                case "face":
                    filterPhotos("Şapka");
                    break;
                case "ustbeden":
                    filterPhotos("T-Shirt");
                    filterPhotos("Gömlek");
                    break;
                case "altbeden":
                    filterPhotos("Pantalon");
                    filterPhotos("Şort");
                    break;
                case "ayak":
                    filterPhotos("Ayakkabı");
                    break;
            }
        }

        RecyclerView recyclerView = findViewById(R.id.RecyclerViewCabineRoomClothes);
        recyclerView.setLayoutManager(new LinearLayoutManager(CabinRoomClothesPhotoActivity.this));
        CabinRoomClothesAdapter cabinRoomClothesAdapter = new CabinRoomClothesAdapter(commbinClothes,CabinRoomClothesPhotoActivity.this);
        recyclerView.setAdapter(cabinRoomClothesAdapter);
    }

    public void filterPhotos(String ClothesType){
        for(Drawer drawer : Drawer.drawerArrayList){
            for(Clothes clothes : drawer.getClothesArrayList()){
                if(clothes.getClothesType().matches(ClothesType)){
                    commbinClothes.add(clothes);
                }
            }
        }
    }

}