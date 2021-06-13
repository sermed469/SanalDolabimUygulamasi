package tr.yildiz.edu.sermedkerim;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tr.yildiz.edu.sermedkerim.Database.FeedReaderContract;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderDbHelper;

public class ClothesImagesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addClothesImage;
    ClothesImageAdapter clothesImageAdapter;
    ArrayList<Bitmap> images = new ArrayList<>();
    ArrayList<byte[]> imageBytes = new ArrayList<>();
    public static ArrayList<String> stringArrayList = new ArrayList<>();
    static int position;
    static final int REQUEST_IMAGE_OPEN = 100;
    Uri URI = null;
    FeedReaderDbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_images);

        stringArrayList.clear();

        getReferences();

        Intent intent = getIntent();
        position = intent.getIntExtra("drawerposition",0);

        //images = Drawer.drawerArrayList.get(position).getClothes();

        dbHelper = new FeedReaderDbHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        String[] projection = {
                //BaseColumns._ID,
                FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_PHOTO,
                FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_BYTE,
                FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_DRAWER
        };

        String selection = FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_DRAWER + " = ?";
        String[] selectionArgs = { (position + 1) + ". çekmece" };

        String sortOrder =
                FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_BYTE + " ASC";

        Cursor cursor = db.query(FeedReaderContract.FeedEntryClothesPhoto.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);

        List itemIds = new ArrayList<>();

        while(cursor.moveToNext()) {
            //long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntryClothesPhoto._ID));
            imageBytes.add(cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_PHOTO)));
            stringArrayList.add(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_BYTE)));
            //System.out.println(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_BYTE)));
            //itemIds.add(itemId);
        }

        cursor.close();

        for (byte[] b : imageBytes){
            images.add(BitmapFactory.decodeByteArray(b,0,b.length));
        }

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
                    //Drawer.drawerArrayList.get(position).getClothes().add(selectedImageBitmap);
                    //Drawer.drawerArrayList.get(position).setClothes(Drawer.drawerArrayList.get(position).getClothes());

                    ContentValues values = new ContentValues();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    values.put(FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_PHOTO, byteArray);
                    values.put(FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_BYTE, byteArray.toString());
                    values.put(FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_DRAWER, (position + 1) + ". çekmece");
                    long newRowId = db.insert(FeedReaderContract.FeedEntryClothesPhoto.TABLE_NAME, null, values);
                    images.add(selectedImageBitmap);
                }
                else {
                    //Drawer.drawerArrayList.get(position).getClothes().remove(p);
                    //Drawer.drawerArrayList.get(position).getClothes().add(p,selectedImageBitmap);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    ContentValues values = new ContentValues();

                    values.put(FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_PHOTO, byteArray);
                    values.put(FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_BYTE, byteArray.toString());

                    String selection = FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_BYTE + " LIKE ?";
                    String[] selectionArgs = {stringArrayList.get(p)};

                    String selection1 = FeedReaderContract.FeedEntryClothes.COLUMN_NAME_BYTE + " LIKE ?";
                    String[] selectionArgs1 = { stringArrayList.get(p) };
                    int deletedRows1 = db.delete(FeedReaderContract.FeedEntryClothes.TABLE_NAME, selection, selectionArgs);

                    for (String s : stringArrayList){
                        System.out.println("string" + s);
                    }

                    int count = db.update( FeedReaderContract.FeedEntryClothesPhoto.TABLE_NAME, values, selection, selectionArgs);

                    stringArrayList.remove(p);
                    stringArrayList.add(byteArray.toString());
                    images.remove(p);
                    images.add(selectedImageBitmap);
                }
                //images = Drawer.drawerArrayList.get(position).getClothes();
                clothesImageAdapter.notifyDataSetChanged();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}