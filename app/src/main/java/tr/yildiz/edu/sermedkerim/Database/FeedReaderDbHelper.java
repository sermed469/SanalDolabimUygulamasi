package tr.yildiz.edu.sermedkerim.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tr.yildiz.edu.sermedkerim.Database.FeedReaderContract;

public class FeedReaderDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntryDrawer.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntryDrawer._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntryDrawer.COLUMN_NAME_NUM + " TEXT)";

    private static final String SQL_CREATE_ENTRIES2 =
            "CREATE TABLE " + FeedReaderContract.FeedEntryClothesPhoto.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntryClothesPhoto._ID + "INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_PHOTO + " BLOB," +
                    FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_BYTE + " TEXT," +
                    FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_DRAWER + " TEXT," +
                    " FOREIGN KEY (" + FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_DRAWER + ") REFERENCES " + FeedReaderContract.FeedEntryDrawer.TABLE_NAME + "(" + FeedReaderContract.FeedEntryDrawer.COLUMN_NAME_NUM + "));";

    private static final String SQL_CREATE_ENTRIES3 =
            "CREATE TABLE " + FeedReaderContract.FeedEntryClothes.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntryClothes._ID + "INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntryClothes.COLUMN_NAME_TYPE + " TEXT," +
                    FeedReaderContract.FeedEntryClothes.COLUMN_NAME_COLOR + " TEXT," +
                    FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DESEN + " TEXT," +
                    FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DATE + " TEXT," +
                    FeedReaderContract.FeedEntryClothes.COLUMN_NAME_PRICE + " TEXT," +
                    FeedReaderContract.FeedEntryClothes.COLUMN_NAME_PHOTO + " BLOB," +
                    FeedReaderContract.FeedEntryClothes.COLUMN_NAME_BYTE + " TEXT," +
                    FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DRAWER + " TEXT," +
                    " FOREIGN KEY (" + FeedReaderContract.FeedEntryClothes.COLUMN_NAME_DRAWER + ") REFERENCES " + FeedReaderContract.FeedEntryDrawer.TABLE_NAME + "(" + FeedReaderContract.FeedEntryDrawer.COLUMN_NAME_NUM + ")," +
                    " FOREIGN KEY (" + FeedReaderContract.FeedEntryClothes.COLUMN_NAME_BYTE + ") REFERENCES " + FeedReaderContract.FeedEntryClothesPhoto.TABLE_NAME + "(" + FeedReaderContract.FeedEntryClothesPhoto.COLUMN_NAME_BYTE + "));";

    private static final String SQL_CREATE_ENTRIES4 =
            "CREATE TABLE " + FeedReaderContract.FeedEntryEtkinlik.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntryEtkinlik._ID + "INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_NAME + " TEXT," +
                    FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_TYPE + " TEXT," +
                    FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_DATE + " TEXT," +
                    FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_LOCATION + " TEXT)";

    private static final String SQL_CREATE_ENTRIES5 =
            "CREATE TABLE " + FeedReaderContract.FeedEntryEtkinlikPhoto.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntryEtkinlikPhoto._ID + "INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_TYPE + " TEXT," +
                    FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_COLOR + " TEXT," +
                    FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_DESEN + " TEXT," +
                    FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_DATE + " TEXT," +
                    FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_PRICE + " TEXT," +
                    FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_PHOTO + " BLOB," +
                    FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_ETKINLIK + " TEXT," +
                    " FOREIGN KEY (" + FeedReaderContract.FeedEntryEtkinlikPhoto.COLUMN_NAME_ETKINLIK + ") REFERENCES " + FeedReaderContract.FeedEntryEtkinlik.TABLE_NAME + "(" + FeedReaderContract.FeedEntryEtkinlik.COLUMN_NAME_NAME + "));";

    private static final String SQL_CREATE_ENTRIES6 =
            "CREATE TABLE " + FeedReaderContract.FeedEntryCombine.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntryCombine._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntryCombine.COLUMN_NAME_NUM + " TEXT," +
                    FeedReaderContract.FeedEntryCombine.COLUMN_NAME_HEAD + " BLOB," +
                    FeedReaderContract.FeedEntryCombine.COLUMN_NAME_FACE + " BLOB," +
                    FeedReaderContract.FeedEntryCombine.COLUMN_NAME_USTBEDEN + " BLOB," +
                    FeedReaderContract.FeedEntryCombine.COLUMN_NAME_ALTBEDEN + " BLOB," +
                    FeedReaderContract.FeedEntryCombine.COLUMN_NAME_AYAK + " BLOB)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntryDrawer.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES2 =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntryClothesPhoto.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES3 =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntryClothes.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES4 =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntryEtkinlik.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES5 =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntryEtkinlikPhoto.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES6 =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntryCombine.TABLE_NAME;

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES2);
        db.execSQL(SQL_CREATE_ENTRIES3);
        db.execSQL(SQL_CREATE_ENTRIES4);
        db.execSQL(SQL_CREATE_ENTRIES5);
        db.execSQL(SQL_CREATE_ENTRIES6);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES6);
        db.execSQL(SQL_DELETE_ENTRIES5);
        db.execSQL(SQL_DELETE_ENTRIES4);
        db.execSQL(SQL_DELETE_ENTRIES3);
        db.execSQL(SQL_DELETE_ENTRIES2);
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static void onCreate1(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES2);
        db.execSQL(SQL_CREATE_ENTRIES3);
        db.execSQL(SQL_CREATE_ENTRIES4);
        db.execSQL(SQL_CREATE_ENTRIES5);
        db.execSQL(SQL_CREATE_ENTRIES6);
    }
    public static void onUpgrade1(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES6);
        db.execSQL(SQL_DELETE_ENTRIES5);
        db.execSQL(SQL_DELETE_ENTRIES4);
        db.execSQL(SQL_DELETE_ENTRIES3);
        db.execSQL(SQL_DELETE_ENTRIES2);
        db.execSQL(SQL_DELETE_ENTRIES);
    }
}
