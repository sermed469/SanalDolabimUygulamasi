package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import tr.yildiz.edu.sermedkerim.Classes.Clothes;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderContract;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderDbHelper;

public class ShowCombineScreen extends AppCompatActivity {

    ImageView head,face,ustbeden,altbeden,ayak;
    Button save;
    FeedReaderDbHelper dbHelper;
    SQLiteDatabase db;

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

                dbHelper = new FeedReaderDbHelper(getApplicationContext());
                db = dbHelper.getWritableDatabase();

                String[] projection = {
                        //BaseColumns._ID,
                        FeedReaderContract.FeedEntryCombine.COLUMN_NAME_NUM,
                };

                Cursor cursor = db.query(FeedReaderContract.FeedEntryCombine.TABLE_NAME,projection,null,null,null,null,null);

                int count = 0;

                while(cursor.moveToNext()) {
                    count++;
                }

                cursor.close();

                ContentValues values = new ContentValues();
                values.put(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_NUM,(count+1) + ". Kombin");
                values.put(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_HEAD,getBitmapBytes(Clothes.combine.get("head").getPhoto()));
                values.put(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_FACE,getBitmapBytes(Clothes.combine.get("face").getPhoto()));
                values.put(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_USTBEDEN,getBitmapBytes(Clothes.combine.get("ustbeden").getPhoto()));
                values.put(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_ALTBEDEN,getBitmapBytes(Clothes.combine.get("altbeden").getPhoto()));
                values.put(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_AYAK,getBitmapBytes(Clothes.combine.get("ayak").getPhoto()));
                long newRowId = db.insert(FeedReaderContract.FeedEntryCombine.TABLE_NAME, null, values);

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

    public byte[] getBitmapBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }
}