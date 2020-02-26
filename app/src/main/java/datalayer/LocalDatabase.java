package datalayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabase extends SQLiteOpenHelper {
    // Database Version
    static final int DATABASE_VERSION = 2;
    // Database Name
    static final String DATABASE_NAME = "feedDB";
//Db Columns
static final String DATABASE_TABLE = "feedTable";
    static final String ID = "id";
    static final String TITLE = "title";
    static final String LINK = "link";
    static final String SOURCE = "source";
    static final String DESCRIPTION = "description";
    static final String IMAGEURL = "imageUrl";
    static final String DATEOFPUBLICATION = "dateOfPublication";
    static final String TIMESAVED = "timeSaved";
    public static final String[] COLUMNS = {ID,TITLE,LINK,SOURCE,IMAGEURL,DESCRIPTION,DATEOFPUBLICATION,TIMESAVED};

    LocalDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_FEED_TABLE = "CREATE TABLE IF NOT EXISTS "+DATABASE_TABLE+"(" +
                ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITLE+" VARCHAR ," +
                LINK+" VARCHAR ," +
                SOURCE+" VARCHAR ," +
                DESCRIPTION+" VARCHAR ," +
                IMAGEURL+" VARCHAR ," +
                DATEOFPUBLICATION+" VARCHAR ," +
                TIMESAVED+" DATETIME DEFAULT CURRENT_TIMESTAMP);";

        // create feeds table
        db.execSQL(CREATE_FEED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS feedTable");

        // create fresh books table
        this.onCreate(db);
    }
}
