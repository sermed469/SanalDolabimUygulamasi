package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class ShowSavedCombineActivity extends AppCompatActivity {

    ImageView head,face,ustbeden,altbeden,ayak;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved_combine);

        Intent intent = getIntent();
        position = intent.getIntExtra("CombinePosition",0);

        getReferences();

        head.setImageBitmap(Clothes.combines.get(position)[0]);
        face.setImageBitmap(Clothes.combines.get(position)[1]);
        ustbeden.setImageBitmap(Clothes.combines.get(position)[2]);
        altbeden.setImageBitmap(Clothes.combines.get(position)[3]);
        ayak.setImageBitmap(Clothes.combines.get(position)[4]);
    }

    public void getReferences(){
        head = findViewById(R.id.imageViewCombineHead2);
        face = findViewById(R.id.imageViewCombineFace2);
        ustbeden = findViewById(R.id.imageViewCombineUstBeden2);
        altbeden = findViewById(R.id.imageViewCombineAltBeden2);
        ayak = findViewById(R.id.imageViewCombineAyak2);
    }
}