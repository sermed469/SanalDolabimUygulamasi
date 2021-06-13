package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.util.ArrayList;

import tr.yildiz.edu.sermedkerim.Adapter.CombineListAdapter;
import tr.yildiz.edu.sermedkerim.Classes.Combine;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderContract;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderDbHelper;

public class CombineListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FeedReaderDbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine_list);

        dbHelper = new FeedReaderDbHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        String[] projection = {
                //BaseColumns._ID,
                FeedReaderContract.FeedEntryCombine.COLUMN_NAME_NUM,
                FeedReaderContract.FeedEntryCombine.COLUMN_NAME_HEAD,
                FeedReaderContract.FeedEntryCombine.COLUMN_NAME_FACE,
                FeedReaderContract.FeedEntryCombine.COLUMN_NAME_USTBEDEN,
                FeedReaderContract.FeedEntryCombine.COLUMN_NAME_ALTBEDEN,
                FeedReaderContract.FeedEntryCombine.COLUMN_NAME_AYAK,
        };

        Cursor cursor = db.query(FeedReaderContract.FeedEntryCombine.TABLE_NAME,projection,null,null,null,null,null);

        ArrayList<Combine> combineList = new ArrayList<>();

        while(cursor.moveToNext()) {

            String number = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_NUM));
            byte[] bytes = cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_HEAD));
            Bitmap head = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
            bytes = cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_FACE));
            Bitmap face = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
            bytes = cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_USTBEDEN));
            Bitmap ustbeden = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
            bytes = cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_ALTBEDEN));
            Bitmap altbeden = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
            bytes = cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_AYAK));
            Bitmap ayak = BitmapFactory.decodeByteArray(bytes,0, bytes.length);

            Combine combine = new Combine(head,face,ustbeden,altbeden,ayak,number);
            combineList.add(combine);
        }

        cursor.close();

        recyclerView = findViewById(R.id.RecyclerViewCombines);
        recyclerView.setLayoutManager(new LinearLayoutManager(CombineListActivity.this));
        CombineListAdapter adapter = new CombineListAdapter(combineList,CombineListActivity.this);
        recyclerView.setAdapter(adapter);
    }
}