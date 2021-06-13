package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import tr.yildiz.edu.sermedkerim.Adapter.AllClothesListAdapter;
import tr.yildiz.edu.sermedkerim.Classes.Clothes;
import tr.yildiz.edu.sermedkerim.Classes.Etkinlik;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderContract;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderDbHelper;

public class ActivityDetails extends AppCompatActivity {

    EditText ActivityName,ActivityType;
    TextView ActivityDate, Location;
    RecyclerView recyclerView;
    Button button;
    ImageButton imageButton;
    ArrayList<Clothes> clothesArrayList = new ArrayList<>();
    FeedReaderDbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getReferences();

        /*for (Drawer drawer : Drawer.drawerArrayList){
            for(Clothes clothes : drawer.getClothesArrayList()){
                clothesArrayList.add(clothes);
            }
        }*/

        dbHelper = new FeedReaderDbHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

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

        SharedPreferences sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("clotheslist",1);
        editor.apply();

        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityDetails.this));
        AllClothesListAdapter adapter = new AllClothesListAdapter(clothesArrayList,ActivityDetails.this);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Etkinlik etkinlik = new Etkinlik(ActivityName.getText().toString(),ActivityType.getText().toString(),ActivityDate.getText().toString(),Location.getText().toString(),Clothes.selectedClothes);
                Etkinlik.etkinlikler.add(etkinlik);

                ContentValues values = new ContentValues();
                values.put(FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_NAME,ActivityName.getText().toString());
                values.put(FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_TYPE,ActivityType.getText().toString());
                values.put(FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_DATE,ActivityDate.getText().toString());
                values.put(FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_LOCATION,Location.getText().toString());
                long newRowId = db.insert(FeedReaderContract.FeedEntryEtkinlik.TABLE_NAME, null, values);

                for(Clothes clothes : Clothes.selectedClothes){
                    values.put(FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_ETKINLIK,ActivityName.getText().toString());
                    values.put(FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_TYPE,clothes.getClothesType());
                    values.put(FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_COLOR,clothes.getColor());
                    values.put(FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_DESEN,clothes.getFigure());
                    values.put(FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_DATE,clothes.getDate());
                    values.put(FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_PRICE,clothes.getPrice());

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    clothes.getPhoto().compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    values.put(FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_PHOTO,byteArray);
                    long newRowId1 = db.insert(FeedReaderContract.FeedEntryEtkinlik.TABLE_NAME, null, values);
                }

                Intent intent = new Intent(ActivityDetails.this,AddActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        Location.setText(sharedPreferences.getString("address","Location"));
    }

    public void getReferences(){
        recyclerView = findViewById(R.id.recyclerViewChooseClothes);
        button = findViewById(R.id.buttonSaveActivity);
        ActivityDate = findViewById(R.id.textViewSelectActivityDate);
        ActivityName = findViewById(R.id.editTextActivityName);
        ActivityType = findViewById(R.id.editTextActivityType);
        imageButton = findViewById(R.id.imageButtonActivityLocation);
        Location = findViewById(R.id.textViewActivityLocationInfo);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(ActivityDate);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void selectLocation(View v){
        Intent intent = new Intent(ActivityDetails.this,MapsActivity.class);
        startActivity(intent);
    }
}