package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ShowCombineScreen extends AppCompatActivity {

    ImageView head,face,ustbeden,altbeden,ayak;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_combine_screen);

        getReferences();

        /*head.setImageBitmap(Clothes.cabinRoomClothes.get(0));
        face.setImageBitmap(Clothes.cabinRoomClothes.get(1));
        ustbeden.setImageBitmap(Clothes.cabinRoomClothes.get(2));
        altbeden.setImageBitmap(Clothes.cabinRoomClothes.get(3));
        ayak.setImageBitmap(Clothes.cabinRoomClothes.get(4));*/

        if(Clothes.combine.get("head") != null){
            head.setImageBitmap(Clothes.combine.get("head").getPhoto());
        }
        if(Clothes.combine.get("face") != null){
            face.setImageBitmap(Clothes.combine.get("face").getPhoto());
        }
        if(Clothes.combine.get("ustbeden") != null){
            ustbeden.setImageBitmap(Clothes.combine.get("ustbeden").getPhoto());
        }
        if(Clothes.combine.get("altbeden") != null){
            altbeden.setImageBitmap(Clothes.combine.get("altbeden").getPhoto());
        }
        if(Clothes.combine.get("ayak") != null){
            ayak.setImageBitmap(Clothes.combine.get("ayak").getPhoto());
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap[] bitmaps = {Clothes.combine.get("head").getPhoto(),Clothes.combine.get("face").getPhoto(),Clothes.combine.get("ustbeden").getPhoto(),Clothes.combine.get("altbeden").getPhoto(),Clothes.combine.get("ayak").getPhoto()};
                Clothes.combines.add(bitmaps);

                Clothes.combine.put("head",null);
                Clothes.combine.put("face",null);
                Clothes.combine.put("ustbeden",null);
                Clothes.combine.put("altbeden",null);
                Clothes.combine.put("ayak",null);

                Intent intent = new Intent(ShowCombineScreen.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getReferences(){
        head = findViewById(R.id.imageViewCombineHead);
        face = findViewById(R.id.imageViewCombineFace);
        ustbeden = findViewById(R.id.imageViewCombineUstBeden);
        altbeden = findViewById(R.id.imageViewCombineAltBeden);
        ayak = findViewById(R.id.imageViewCombineAyak);
        save = findViewById(R.id.buttonSaveCombine);
    }
}