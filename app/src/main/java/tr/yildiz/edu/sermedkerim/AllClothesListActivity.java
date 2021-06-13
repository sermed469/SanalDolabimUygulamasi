package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import tr.yildiz.edu.sermedkerim.Adapter.AllClothesListAdapter;
import tr.yildiz.edu.sermedkerim.Classes.Clothes;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderContract;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderDbHelper;

public class AllClothesListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Clothes> clothesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_clothes_list);

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

        String sortOrder =
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_BYTE + " ASC";

        Cursor cursor = db.query(FeedReaderContract.FeedEntryClothes.TABLE_NAME,projection,null,null,null,null,sortOrder);

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

            clothesArrayList.add(clothes);

  //          itemIds.add(itemId);
        }

        cursor.close();


       /* for (Drawer drawer : Drawer.drawerArrayList){
            for(Clothes clothes : drawer.getClothesArrayList()){
                clothesArrayList.add(clothes);
            }
        }*/

        SharedPreferences sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("clotheslist",0);
        editor.apply();

        recyclerView = findViewById(R.id.AllClothesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllClothesListActivity.this));
        AllClothesListAdapter adapter = new AllClothesListAdapter(clothesArrayList,AllClothesListActivity.this);
        recyclerView.setAdapter(adapter);

    }
}