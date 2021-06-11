package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ClothesActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener{

    int drawerposition;
    int imageposition;
    TextView clothesType;
    TextView desen;
    TextView date;
    TextView color;
    EditText price;
    ImageView imageView;
    Button button;
    DatePickerDialog.OnDateSetListener dateSetListener;
    boolean isTypeClicked = false;
    boolean isDesenClicked = false;
    boolean isRenkClicked = false;
    String[] ClothesTypes = {"Şapka","Ayakkabı","T-Shirt","Pantalon","Gömlek","Şort"};
    String[] Desenler = {"Düz","Çizgili","Kareli"};
    String[] Renkler = {"Kırmızı","Mavi","Yeşil","Mor","Sarı","Beyaz","Siyah","Kahverengi","Pembe"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        Intent intent = getIntent();
        imageposition = intent.getIntExtra("imageposition",0);
        drawerposition = intent.getIntExtra("DrawerPosition",0);

        Clothes clothes = findClothes();

        getReferences();

        imageView.setImageBitmap(Drawer.drawerArrayList.get(drawerposition).clothes.get(imageposition));

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
                    Clothes newClothes = new Clothes(clothesType.getText().toString(),color.getText().toString(),desen.getText().toString(),date.getText().toString(),Float.parseFloat(price.getText().toString()),Drawer.drawerArrayList.get(drawerposition).clothes.get(imageposition));
                    ArrayList<Clothes> clothesArrayList = Drawer.drawerArrayList.get(drawerposition).getClothesArrayList();
                    clothesArrayList.add(newClothes);
                    Drawer.drawerArrayList.get(drawerposition).setClothesArrayList(clothesArrayList);
                    Intent intentToClothesList = new Intent(ClothesActivity.this,ClothesImagesActivity.class);
                    intentToClothesList.putExtra("drawerposition",drawerposition);
                    startActivity(intentToClothesList);
                    finish();
                }
            });

        }
        else{
            button.setVisibility(View.INVISIBLE);
            clothesType.setText(clothes.getClothesType());
            color.setText(clothes.getColor());
            desen.setText(clothes.getFigure());
            price.setText(clothes.getPrice().toString() + " TL");
            price.setEnabled(false);
            date.setText(clothes.getDate());
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

    public Clothes findClothes(){
        for (Clothes clothes : Drawer.drawerArrayList.get(drawerposition).getClothesArrayList()){
            if(clothes.photo == Drawer.drawerArrayList.get(drawerposition).getClothes().get(imageposition)){
                return clothes;
            }
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