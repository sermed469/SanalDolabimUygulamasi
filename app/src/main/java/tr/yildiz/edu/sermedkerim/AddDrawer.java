package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddDrawer extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addDrawer;
    ArrayList<Drawer> drawers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drawer);

        getReferences();

        drawers = Drawer.drawerArrayList;
        recyclerView.setLayoutManager(new LinearLayoutManager(AddDrawer.this));
        DrawerAdapter drawerAdapter = new DrawerAdapter(drawers,AddDrawer.this);
        recyclerView.setAdapter(drawerAdapter);

        addDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddDrawer.this);
                builder.setTitle("Çekmece Ekle");
                builder.setMessage("Yeni bir çekmece eklemek istediğinize emin misiniz?");
                builder.setNegativeButton("Hayır", null);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String DrawerNum = (drawers.size() + 1) + ". çekmece";
                        drawers.add(new Drawer(DrawerNum,new ArrayList<Bitmap>(),new ArrayList<Clothes>()));
                        drawerAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });
    }

    public void getReferences(){
        addDrawer = findViewById(R.id.floatingActionButtonAddDrawer);
        recyclerView = findViewById(R.id.RecyclerViewDrawer);
    }
}