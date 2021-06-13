package tr.yildiz.edu.sermedkerim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    Button gotoCabinRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       /* FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntryDrawer.TABLE_NAME);
        dbHelper.onDowngrade(db,0,1);
        dbHelper.onCreate(db);*/

        gotoCabinRoom = findViewById(R.id.buttonGoToCabin);

        gotoCabinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CabinRoomActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.add_drawer:
                Intent intent = new Intent(MainActivity.this,AddDrawer.class);
                startActivity(intent);
                break;
            case R.id.allclothes:
                Intent intentToAllClothesList = new Intent(MainActivity.this,AllClothesListActivity.class);
                startActivity(intentToAllClothesList);
                break;
            case R.id.add_activity:
                Intent intentToAddActivity = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intentToAddActivity);
                break;
            case R.id.allcombines:
                Intent intentToCombineList = new Intent(MainActivity.this,CombineListActivity.class);
                startActivity(intentToCombineList);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}