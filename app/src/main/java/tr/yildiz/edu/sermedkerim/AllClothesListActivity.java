package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;

public class AllClothesListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Clothes> clothesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_clothes_list);

        for (Drawer drawer : Drawer.drawerArrayList){
            for(Clothes clothes : drawer.getClothesArrayList()){
                clothesArrayList.add(clothes);
            }
        }

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