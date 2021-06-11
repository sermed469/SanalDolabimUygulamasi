package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CabinRoomActivity extends AppCompatActivity {

    ImageView head,face,ustbeden,altbeden,ayak;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabin_room);

        getRefences();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CabinRoomActivity.this,ShowCombineScreen.class);
                startActivity(intent);
            }
        });
    }

    public void getRefences(){
        head = findViewById(R.id.imageViewHat);
        face = findViewById(R.id.imageViewFace);
        ustbeden = findViewById(R.id.imageViewUstBeden);
        altbeden = findViewById(R.id.imageViewAltBeden);
        ayak = findViewById(R.id.imageViewAyak);
        button = findViewById(R.id.buttonShowCombine);
    }

    public void goToHead(View view){
        Intent intent = new Intent(CabinRoomActivity.this,CabinRoomClothesPhotoActivity.class);
        intent.putExtra("kabinbolum","head");
        startActivity(intent);
        finish();
    }

    public void goToFace(View view){
        Intent intent = new Intent(CabinRoomActivity.this,CabinRoomClothesPhotoActivity.class);
        intent.putExtra("kabinbolum","face");
        startActivity(intent);
        finish();
    }

    public void goToUstBeden(View view){
        Intent intent = new Intent(CabinRoomActivity.this,CabinRoomClothesPhotoActivity.class);
        intent.putExtra("kabinbolum","ustbeden");
        startActivity(intent);
        finish();
    }

    public void goToAltBeden(View view){
        Intent intent = new Intent(CabinRoomActivity.this,CabinRoomClothesPhotoActivity.class);
        intent.putExtra("kabinbolum","altbeden");
        startActivity(intent);
        finish();
    }

    public void goToAyak(View view){
        Intent intent = new Intent(CabinRoomActivity.this,CabinRoomClothesPhotoActivity.class);
        intent.putExtra("kabinbolum","ayak");
        startActivity(intent);
        finish();
    }
}