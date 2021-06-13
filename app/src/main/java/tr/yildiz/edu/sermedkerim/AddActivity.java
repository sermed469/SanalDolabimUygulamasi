package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import tr.yildiz.edu.sermedkerim.Adapter.ActivityListAdapter;
import tr.yildiz.edu.sermedkerim.Classes.Clothes;
import tr.yildiz.edu.sermedkerim.Classes.Etkinlik;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderContract;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderDbHelper;

public class AddActivity extends AppCompatActivity {

    FloatingActionButton addActivity;
    RecyclerView recyclerView;
    FeedReaderDbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getReferences();

        dbHelper = new FeedReaderDbHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        String[] projection1 = {
                //BaseColumns._ID,
                FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_NAME,
                FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_LOCATION,
                FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_DATE,
                FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_TYPE,
        };

        String sortOrder1 =
                FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_DATE + " DESC";

        Cursor cursor1 = db.query(FeedReaderContract.FeedEntryEtkinlik.TABLE_NAME,projection1,null,null,null,null,sortOrder1);

        ArrayList<Etkinlik> etkinliks = new ArrayList<>();

        while(cursor1.moveToNext()) {
            String name = cursor1.getString(cursor1.getColumnIndex(FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_NAME));
            String type = cursor1.getString(cursor1.getColumnIndex(FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_TYPE));
            String date = cursor1.getString(cursor1.getColumnIndex(FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_DATE));
            String location = cursor1.getString(cursor1.getColumnIndex(FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_LOCATION));

            String[] projection = {
                    //BaseColumns._ID,
                    FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_TYPE,
                    FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_COLOR,
                    FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_DESEN,
                    FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_DATE,
                    FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_PRICE,
                    FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_PHOTO,
                    FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_ETKINLIK,
            };

            String selection = FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_ETKINLIK + " LIKE ?";
            String[] selectionArgs = {name};

            String sortOrder =
                    FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_DATE + " DESC";

            Cursor cursor = db.query(FeedReaderContract.FeedEntryEtkinlikPhoto.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);

            ArrayList<Clothes> clothesArrayList = new ArrayList<>();

            while(cursor.moveToNext()) {

                String clothestype = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_TYPE));
                String clothescolor = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_COLOR));
                String clothesdesen = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_DESEN));
                String clothesdate = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_DATE));
                String clothesprice = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_PRICE));

                byte[] byteClothes = cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_PHOTO));

                Clothes clothes = new Clothes(clothestype,clothescolor,clothesdesen,clothesdate,Float.parseFloat(clothesprice),BitmapFactory.decodeByteArray(byteClothes,0,byteClothes.length));

                clothesArrayList.add(clothes);
            }

            cursor.close();

            Etkinlik etkinlik = new Etkinlik(name,type,location,date,clothesArrayList);

            etkinliks.add(etkinlik);
        }

        cursor1.close();

        recyclerView.setLayoutManager(new LinearLayoutManager(AddActivity.this));
        ActivityListAdapter adapter = new ActivityListAdapter(etkinliks,AddActivity.this);
        recyclerView.setAdapter(adapter);

        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, ActivityDetails.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void getReferences(){
        addActivity = findViewById(R.id.floatingActionButtonAddActivity);
        recyclerView = findViewById(R.id.RecyclerViewEtkinlikList);
    }


}