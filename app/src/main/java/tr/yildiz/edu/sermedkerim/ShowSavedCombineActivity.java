package tr.yildiz.edu.sermedkerim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import tr.yildiz.edu.sermedkerim.Database.FeedReaderContract;
import tr.yildiz.edu.sermedkerim.Database.FeedReaderDbHelper;

public class ShowSavedCombineActivity extends AppCompatActivity {

    ImageView head,face,ustbeden,altbeden,ayak;
    String position;
    FeedReaderDbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved_combine);

        Intent intent = getIntent();
        position = intent.getStringExtra("CombinePosition");

        getReferences();

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

        String selection = FeedReaderContract.FeedEntryCombine.COLUMN_NAME_NUM + " = ?";
        String[] selectionArgs = { position };

        Cursor cursor = db.query(FeedReaderContract.FeedEntryCombine.TABLE_NAME,projection,selection,selectionArgs,null,null,null);

        Bitmap headBitmap = null, faceBitmap = null, ustbedenBitmap = null, altbedenBitmap = null, ayakBitmap = null;

        while(cursor.moveToNext()) {

            String number = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_NUM));
            byte[] bytes = cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_HEAD));
            headBitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
            bytes = cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_FACE));
            faceBitmap= BitmapFactory.decodeByteArray(bytes,0, bytes.length);
            bytes = cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_USTBEDEN));
            ustbedenBitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
            bytes = cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_ALTBEDEN));
            altbedenBitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
            bytes = cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntryCombine.COLUMN_NAME_AYAK));
            ayakBitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
        }

        cursor.close();

        head.setImageBitmap(headBitmap);
        face.setImageBitmap(faceBitmap);
        ustbeden.setImageBitmap(ustbedenBitmap);
        altbeden.setImageBitmap(altbedenBitmap);
        ayak.setImageBitmap(ayakBitmap);
    }

    public void getReferences(){
        head = findViewById(R.id.imageViewCombineHead2);
        face = findViewById(R.id.imageViewCombineFace2);
        ustbeden = findViewById(R.id.imageViewCombineUstBeden2);
        altbeden = findViewById(R.id.imageViewCombineAltBeden2);
        ayak = findViewById(R.id.imageViewCombineAyak2);
    }
}