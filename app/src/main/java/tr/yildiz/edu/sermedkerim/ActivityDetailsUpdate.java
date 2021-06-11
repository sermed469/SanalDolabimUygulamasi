package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityDetailsUpdate extends AppCompatActivity {

    EditText ActivityName,ActivityType;
    TextView ActivityDate, Location;
    RecyclerView recyclerView;
    Button button;
    ImageButton imageButton;
    ImageButton name,type,date,location;
    ArrayList<Clothes> clothesArrayList = new ArrayList<>();
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_update);

        position = getIntent().getIntExtra("updateposition",0);

        getReferences();

        fillInputs();

        for (Drawer drawer : Drawer.drawerArrayList){
            for(Clothes clothes : drawer.getClothesArrayList()){
                clothesArrayList.add(clothes);
            }
        }

        SharedPreferences sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("clotheslist",1);
        editor.apply();

        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityDetailsUpdate.this));
        AllClothesListAdapter adapter = new AllClothesListAdapter(clothesArrayList,ActivityDetailsUpdate.this);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Etkinlik.etkinlikler.get(position).setName(ActivityName.getText().toString());
                Etkinlik.etkinlikler.get(position).setType(ActivityType.getText().toString());
                Etkinlik.etkinlikler.get(position).setDate(ActivityDate.getText().toString());
                Etkinlik.etkinlikler.get(position).setLocation(Location.getText().toString());
                Etkinlik.etkinlikler.get(position).setClothes(Clothes.selectedClothes);
                Intent intent = new Intent(ActivityDetailsUpdate.this,AddActivity.class);
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
        recyclerView = findViewById(R.id.recyclerViewChooseClothesUpdate);
        button = findViewById(R.id.buttonSaveActivityUpdate);
        ActivityDate = findViewById(R.id.textViewSelectActivityDateUpdate);
        ActivityName = findViewById(R.id.editTextActivityNameUpdate);
        ActivityType = findViewById(R.id.editTextActivityTypeUpdate);
        imageButton = findViewById(R.id.imageButtonActivityLocationUpdate);
        Location = findViewById(R.id.textViewActivityLocationInfoUpdate);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(ActivityDate);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void selectLocation(View v){
        Intent intent = new Intent(ActivityDetailsUpdate.this,MapsActivity.class);
        startActivity(intent);
    }

    public void fillInputs(){
        ActivityName.setText(Etkinlik.etkinlikler.get(position).getName());
        ActivityDate.setText(Etkinlik.etkinlikler.get(position).getDate());
        ActivityType.setText(Etkinlik.etkinlikler.get(position).getType());
        Location.setText(Etkinlik.etkinlikler.get(position).getLocation());
    }

    public void deleteActivityName(View view){
        Etkinlik.etkinlikler.get(position).setName("");
        ActivityName.setText("");
    }

    public void deleteActivityType(View view){
        Etkinlik.etkinlikler.get(position).setType("");
        ActivityType.setText("");
    }

    public void deleteActivityDate(View view){
        Etkinlik.etkinlikler.get(position).setDate("");
        ActivityName.setText("Etkinlik tarihi seçin");
    }

    public void deleteActivityLocation(View view){
        Etkinlik.etkinlikler.get(position).setLocation("");
        Location.setText("Location");
    }

}