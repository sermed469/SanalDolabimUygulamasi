package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityDetails extends AppCompatActivity {

    EditText ActivityName,ActivityType;
    TextView ActivityDate, Location;
    RecyclerView recyclerView;
    Button button;
    ImageButton imageButton;
    ArrayList<Clothes> clothesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getReferences();

        for (Drawer drawer : Drawer.drawerArrayList){
            for(Clothes clothes : drawer.getClothesArrayList()){
                clothesArrayList.add(clothes);
            }
        }

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