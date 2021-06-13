package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import tr.yildiz.edu.sermedkerim.Classes.Clothes;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderContract;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderDbHelper;

public class ClothesActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener{

    int drawerposition;
    String imageposition;
    TextView clothesType;
    TextView desen;
    TextView date;
    TextView color;
    EditText price;
    ImageView imageView;
    Button button;
    DatePickerDialog.OnDateSetListener dateSetListener;
    FeedReaderDbHelper dbHelper;
    SQLiteDatabase db;
    boolean isTypeClicked = false;
    boolean isDesenClicked = false;
    boolean isRenkClicked = false;
    String[] ClothesTypes = {"Şapka","Ayakkabı","T-Shirt","Pantalon","Gömlek","Şort"};
    String[] Desenler = {"Düz","Çizgili","Kareli"};
    String[] Renkler = {"Kırmızı","Mavi","Yeşil","Mor","Sarı","Beyaz","Siyah","Kahverengi","Pembe"};
    ArrayList<byte[]> imageBytes = new ArrayList<>();
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayList<Clothes> clothesArrayList = new ArrayList<>();
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        Intent intent = getIntent();
        imageposition = intent.getStringExtra("imageposition");
        drawerposition = intent.getIntExtra("DrawerPosition",0);

        String clothes = findClothes();

        getReferences();

        dbHelper = new FeedReaderDbHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        String[] projection = {
                //BaseColumns._ID,
                FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_PHOTO,
                FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_BYTE,
                FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_DRAWER
        };

        String selection = FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_DRAWER + " = ? AND " + FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_BYTE + " = ?";
        String[] selectionArgs = { (drawerposition + 1) + ". çekmece", imageposition };

        String sortOrder =
                FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_BYTE + " ASC";

        Cursor cursor = db.query(FeedReaderContract.FeedEntryClothesPhoto.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);

        List itemIds = new ArrayList<>();

        ArrayList<byte[]> imageBytes = new ArrayList<>();

        while(cursor.moveToNext()) {
            //long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntryClothesPhoto._ID));
            imageBytes.add(cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_PHOTO)));
            //System.out.println(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_BYTE)));
            //itemIds.add(itemId);
        }

        cursor.close();

        imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes.get(0),0,imageBytes.get(0).length));

        if (clothes == null) {
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(ClothesActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
                    dialog.getDatePicker().setMaxDate(new Date().getTime());
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });

            dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month + 1;
                    date.setText(dayOfMonth + "/" + month + "/" + year);
                }
            };

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Clothes newClothes = new Clothes(clothesType.getText().toString(),color.getText().toString(),desen.getText().toString(),date.getText().toString(),Float.parseFloat(price.getText().toString()),BitmapFactory.decodeByteArray(imageBytes.get(0),0,imageBytes.get(0).length));
                    //ArrayList<Clothes> clothesArrayList = Drawer.drawerArrayList.get(drawerposition).getClothesArrayList();
                    clothesArrayList.add(newClothes);
                    //Drawer.drawerArrayList.get(drawerposition).setClothesArrayList(clothesArrayList);
                    Intent intentToClothesList = new Intent(ClothesActivity.this,ClothesImagesActivity.class);
                    intentToClothesList.putExtra("drawerposition",drawerposition);

                    ContentValues values = new ContentValues();
                    values.put(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_TYPE,clothesType.getText().toString());
                    values.put(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_COLOR,color.getText().toString());
                    values.put(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DESEN,desen.getText().toString());
                    values.put(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DATE,date.getText().toString());
                    values.put(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_PRICE,price.getText().toString());
                    values.put(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_PHOTO,imageBytes.get(0));
                    values.put(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_BYTE,imageposition);
                    values.put(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DRAWER,(drawerposition + 1) + ". çekmece");
                    long newRowId = db.insert(FeedReaderContract.FeedEntryClothes.TABLE_NAME, null, values);

                    Toast.makeText(ClothesActivity.this,"Kıyafet bilgileri kaydedildi",Toast.LENGTH_SHORT).show();

                    startActivity(intentToClothesList);
                    finish();
                }
            });

        }
        else{
            button.setVisibility(View.INVISIBLE);
            clothesType.setText(clothesArrayList.get(index).getClothesType());
            color.setText(clothesArrayList.get(index).getColor());
            desen.setText(clothesArrayList.get(index).getFigure());
            price.setText(clothesArrayList.get(index).getPrice() + " TL");
            price.setEnabled(false);
            date.setText(clothesArrayList.get(index).getDate());
        }
    }

    public void getReferences(){
        clothesType = findViewById(R.id.textViewClothesType);
        color = findViewById(R.id.textViewColor);
        desen = findViewById(R.id.textViewDesen);
        date = findViewById(R.id.textViewClothesDate);
        price = findViewById(R.id.editTextNumberClothesPrice);
        imageView = findViewById(R.id.imageViewClothesDetailPhoto);
        button = findViewById(R.id.buttonSaveClothesDetails);
    }

    public String findClothes(){

        dbHelper = new FeedReaderDbHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        String[] projection = {
                //BaseColumns._ID,
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_TYPE,
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_COLOR,
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DESEN,
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DATE,
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_PRICE,
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_PHOTO,
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_BYTE,
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DRAWER
        };

        String selection = FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DRAWER + " = ?";
        String[] selectionArgs = { (drawerposition + 1) + ". çekmece" };

        String sortOrder =
                FeedReaderContract.FeedEntryClothes.COLUMN_NAME_BYTE + " ASC";

        Cursor cursor = db.query(FeedReaderContract.FeedEntryClothes.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);

        List itemIds = new ArrayList<>();

        while(cursor.moveToNext()) {
            //long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntryClothesPhoto._ID));
            //stringArrayList.add(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_BYTE)));
            //imageBytes.add(cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_PHOTO)));
            byte[] bytes = cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_PHOTO));
            String type = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_TYPE));
            String color = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_COLOR));
            String figure = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DESEN));
            String date = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DATE));
            String price = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothes.COLUMN_NAME_PRICE));

            Clothes clothes = new Clothes(type,color,figure,date,Float.parseFloat(price),BitmapFactory.decodeByteArray(bytes,0,bytes.length));

            clothesArrayList.add(clothes);
            //System.out.println(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_BYTE)));
            //itemIds.add(itemId);
        }

        cursor.close();

        index = 0;
        for (String s : stringArrayList){
            System.out.println(s);
            if(s.matches(imageposition)){
                return s;
            }
            index++;
        }

        return null;
    }

    public void showClothesTypes(View view){
        isDesenClicked = false;
        isRenkClicked = false;
        isTypeClicked = true;
        DialogPicker newFragment = new DialogPicker(ClothesTypes);
        newFragment.setValueChangeListener(this);
        newFragment.show(getSupportFragmentManager(), "time picker");
    }

    public void showDesen(View view){
        isTypeClicked = false;
        isRenkClicked = false;
        isDesenClicked = true;
        DialogPicker newFragment = new DialogPicker(Desenler);
        newFragment.setValueChangeListener(this);
        newFragment.show(getSupportFragmentManager(), "time picker");
    }

    public void showColors(View view){
        isTypeClicked = false;
        isDesenClicked = false;
        isRenkClicked = true;
        DialogPicker newFragment = new DialogPicker(Renkler);
        newFragment.setValueChangeListener(this);
        newFragment.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if(isTypeClicked){
            clothesType.setText(ClothesTypes[picker.getValue()]);
        }
        else if(isDesenClicked){
            desen.setText(Desenler[picker.getValue()]);
        }
        else if(isRenkClicked){
            color.setText(Renkler[picker.getValue()]);
        }
    }
}