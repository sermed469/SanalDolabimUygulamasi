package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import tr.yildiz.edu.sermedkerim.Adapter.DrawerAdapter;
import tr.yildiz.edu.sermedkerim.Classes.Clothes;
import tr.yildiz.edu.sermedkerim.Classes.Drawer;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderContract;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderDbHelper;

public class AddDrawer extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addDrawer;
    ArrayList<Drawer> drawers = new ArrayList<>();
    ArrayList<Drawer> DrawerNums = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drawer);

        getReferences();

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntryDrawer.COLUMN_NAME_NUM
        };

        String sortOrder =
                FeedReaderContract.FeedEntryDrawer.COLUMN_NAME_NUM + " ASC";

        Cursor cursor = db.query(FeedReaderContract.FeedEntryDrawer.TABLE_NAME,projection,null,null,null,null,sortOrder);

        List itemIds = new ArrayList<>();

        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntryDrawer._ID));
            DrawerNums.add(new Drawer(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryDrawer.COLUMN_NAME_NUM)),new ArrayList<Bitmap>(),new ArrayList<Clothes>()));
            itemIds.add(itemId);
        }

        cursor.close();

        drawers = Drawer.drawerArrayList;
        recyclerView.setLayoutManager(new LinearLayoutManager(AddDrawer.this));
        DrawerAdapter drawerAdapter = new DrawerAdapter(DrawerNums,AddDrawer.this);
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
                        String DrawerNum;
                        if(DrawerNums.size() != 0){
                            DrawerNum = (Integer.parseInt(String.valueOf(DrawerNums.get(DrawerNums.size()-1).getNumber().charAt(0))) + 1) + ". çekmece";
                        }
                        else{
                            DrawerNum = "1. çekmece";
                        }
                        //drawers.add(new Drawer(DrawerNum,new ArrayList<Bitmap>(),new ArrayList<Clothes>()));

                        ContentValues values = new ContentValues();
                        values.put(FeedReaderContract.FeedEntryDrawer.COLUMN_NAME_NUM, DrawerNum);
                        long newRowId = db.insert(FeedReaderContract.FeedEntryDrawer.TABLE_NAME, null, values);
                        DrawerNums.add(new Drawer(DrawerNum,new ArrayList<Bitmap>(),new ArrayList<Clothes>()));
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