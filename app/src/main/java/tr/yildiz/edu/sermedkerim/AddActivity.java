package tr.yildiz.edu.sermedkerim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddActivity extends AppCompatActivity {

    FloatingActionButton addActivity;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getReferences();

        recyclerView.setLayoutManager(new LinearLayoutManager(AddActivity.this));
        ActivityListAdapter adapter = new ActivityListAdapter(Etkinlik.etkinlikler,AddActivity.this);
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