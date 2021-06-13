package tr.yildiz.edu.sermedkerim.Database;

import android.provider.BaseColumns;

public final class FeedReaderContract {

    private FeedReaderContract() {}

    public static class FeedEntryDrawer implements BaseColumns {
        public static final String TABLE_NAME = "Drawer";
        public static final String COLUMN_NAME_NUM = "number";
    }

    public static class FeedEntryClothesPhoto implements BaseColumns {
        public static final String TABLE_NAME = "ClothesPhoto";
        public static final String COLUMN_NAME_PHOTO = "photo";
        public static final String COLUMN_NAME_BYTE = "byteString";
        public static final String COLUMN_NAME_DRAWER = "drawerId";
    }

    public static class FeedEntryClothes implements BaseColumns {
        public static final String TABLE_NAME = "Clothes";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_COLOR = "color";
        public static final String COLUMN_NAME_DESEN = "desen";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_PHOTO = "photo";
        public static final String COLUMN_NAME_BYTE = "byteString";
        public static final String COLUMN_NAME_DRAWER = "drawerId";
    }

    public static class FeedEntryEtkinlik implements BaseColumns {
        public static final String TABLE_NAME = "Etkinlik";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_LOCATION = "location";
    }

    public static class FeedEntryEtkinlikPhoto implements BaseColumns {
        public static final String TABLE_NAME = "EtkinlikPhoto";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_COLOR = "color";
        public static final String COLUMN_NAME_DESEN = "desen";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_PHOTO = "photo";
        public static final String COLUMN_NAME_ETKINLIK = "etkinlik";
    }

    public static class FeedEntryCombine implements BaseColumns {
        public static final String TABLE_NAME = "Combine";
        public static final String COLUMN_NAME_NUM = "num";
        public static final String COLUMN_NAME_HEAD = "head";
        public static final String COLUMN_NAME_FACE = "face";
        public static final String COLUMN_NAME_USTBEDEN = "ustbeden";
        public static final String COLUMN_NAME_ALTBEDEN = "altbeden";
        public static final String COLUMN_NAME_AYAK = "ayak";
    }
}
