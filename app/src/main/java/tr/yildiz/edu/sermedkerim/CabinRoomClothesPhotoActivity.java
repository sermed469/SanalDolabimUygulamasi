package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import tr.yildiz.edu.sermedkerim.Adapter.CabinRoomClothesAdapter;
import tr.yildiz.edu.sermedkerim.Classes.Clothes;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderContract;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderDbHelper;

public class CabinRoomClothesPhotoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Clothes> commbinClothes = new ArrayList<>();
    ArrayList<Clothes> clothesArrayList = new ArrayList<>();
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
        CabinRoomClothesAdapter cabinRoomClothesAdapter = new CabinRoomClothesAdapter(clothesArrayList,CabinRoomClothesPhotoActivity.this);
        recyclerView.setAdapter(cabinRoomClothesAdapter);
    }

    public void filterPhotos(String ClothesType){

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] projection = {
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_TYPE,
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_COLOR,
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DATE,
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_PRICE,
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_PHOTO,
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DESEN,
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_BYTE,
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DRAWER,
        };

        String selection = FeedReaderContract.FeedEntryClothes.COLUMN_NAME_TYPE+ " = ?";
        String[] selectionArgs = { ClothesType };

        String sortOrder =
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_BYTE + " ASC";

        Cursor cursor = db.query(FeedReaderContract.FeedEntryClothes.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);

        List itemIds = new ArrayList<>();

        while(cursor.moveToNext()) {
//            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntryDrawer._ID));

            byte[] bytes = cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_PHOTO));
            String type = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_TYPE));
            String color = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_COLOR));
            String figure = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DESEN));
            String date = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DATE));
            String price = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_PRICE));

            Clothes clothes = new Clothes(type,color,figure,date,Float.parseFloat(price), BitmapFactory.decodeByteArray(bytes,0,bytes.length));
            System.out.println("aaaaaaaaaaaaaaaaaa");
            clothesArrayList.add(clothes);

            //          itemIds.add(itemId);
        }

        cursor.close();


        /*for(Drawer drawer : Drawer.drawerArrayList){
            for(Clothes clothes : drawer.getClothesArrayList()){
                if(clothes.getClothesType().matches(ClothesType)){
                    commbinClothes.add(clothes);
                }
            }
        }*/
    }

}