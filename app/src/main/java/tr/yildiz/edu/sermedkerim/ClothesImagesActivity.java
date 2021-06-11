package tr.yildiz.edu.sermedkerim;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

public class ClothesImagesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addClothesImage;
    ClothesImageAdapter clothesImageAdapter;
    ArrayList<Bitmap> images = new ArrayList<>();
    int position;
    static final int REQUEST_IMAGE_OPEN = 100;
    Uri URI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_images);

        getReferences();

        Intent intent = getIntent();
        position = intent.getIntExtra("drawerposition",0);

        images = Drawer.drawerArrayList.get(position).getClothes();

        recyclerView.setLayoutManager(new LinearLayoutManager(ClothesImagesActivity.this));
        clothesImageAdapter = new ClothesImageAdapter(images,ClothesImagesActivity.this);
        recyclerView.setAdapter(clothesImageAdapter);

        addClothesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("update",0);
                editor.apply();

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_IMAGE_OPEN);
            }
        });
    }

    public void getReferences(){
        recyclerView = findViewById(R.id.RecyclerViewClothesImages);
        addClothesImage = findViewById(R.id.floatingActionButtonAddClothesImages);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap selectedImageBitmap;

        SharedPreferences sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);

        int update = sharedPreferences.getInt("update",0);
        int p = sharedPreferences.getInt("position",-1);

        if(requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK){
            URI = data.getData();
            try {
                if(Build.VERSION.SDK_INT >= 28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),URI);
                    selectedImageBitmap = ImageDecoder.decodeBitmap(source);
                }
                else{
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),URI);
                }

                if(update == 0){
                    Drawer.drawerArrayList.get(position).getClothes().add(selectedImageBitmap);
                    Drawer.drawerArrayList.get(position).setClothes(Drawer.drawerArrayList.get(position).getClothes());
                }
                else {
                    Drawer.drawerArrayList.get(position).getClothes().remove(p);
                    Drawer.drawerArrayList.get(position).getClothes().add(p,selectedImageBitmap);
                }
                images = Drawer.drawerArrayList.get(position).getClothes();
                clothesImageAdapter.notifyDataSetChanged();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}