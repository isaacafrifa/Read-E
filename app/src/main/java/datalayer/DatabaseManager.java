package datalayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.SavedFeed;

public class DatabaseManager {
    private SQLiteDatabase wdb;
    private SQLiteDatabase rdb;

    public DatabaseManager(Context ctx) {
        LocalDatabase helper = new LocalDatabase(ctx, LocalDatabase.DATABASE_NAME, null, LocalDatabase.DATABASE_VERSION);
        wdb = helper.getWritableDatabase();
        rdb = helper.getReadableDatabase();
    }


    //------------------- Calls
    public boolean ADD_FEED(SavedFeed cus){
        ContentValues cv=new ContentValues();
        cv.put(LocalDatabase.TITLE, cus.getTitle());
        cv.put(LocalDatabase.DATEOFPUBLICATION, cus.getDateofpublication());
        cv.put(LocalDatabase.LINK, cus.getLink());
        cv.put(LocalDatabase.DESCRIPTION, cus.getDescription());
        cv.put(LocalDatabase.IMAGEURL, cus.getImageUrl());
        cv.put(LocalDatabase.SOURCE, cus.getSource());

        cv.put(LocalDatabase.TIMESAVED, new SimpleDateFormat("EEE, dd-MM-yyyy h:mm a").format(new Date()));

        //writer inserting into table bus
       long result= wdb.insert(LocalDatabase.DATABASE_TABLE, null, cv);
      //  wdb.close();
        return result != -1;
    }


    public ArrayList<SavedFeed> GET_ALL_FEEDS(){
        ArrayList<SavedFeed> all = new ArrayList<>();
        String POST_QUERY = "SELECT * FROM " + LocalDatabase.DATABASE_TABLE + " ORDER BY " + LocalDatabase.ID + " DESC";
        Cursor cs=rdb.rawQuery(POST_QUERY,null);
        try {

            if(cs.moveToFirst()){
                do{
                    SavedFeed vocab=new SavedFeed();
                    vocab.setId(cs.getInt(cs.getColumnIndex(LocalDatabase.ID)));
                    vocab.setTitle(cs.getString(cs.getColumnIndex(LocalDatabase.TITLE)));
                    vocab.setDateofpublication(cs.getString(cs.getColumnIndex(LocalDatabase.DATEOFPUBLICATION)));
                    vocab.setLink(cs.getString(cs.getColumnIndex(LocalDatabase.LINK)));
                    vocab.setDescription(cs.getString(cs.getColumnIndex(LocalDatabase.DESCRIPTION)));
                    vocab.setImageUrl(cs.getString(cs.getColumnIndex(LocalDatabase.IMAGEURL)));
                    vocab.setSource(cs.getString(cs.getColumnIndex(LocalDatabase.SOURCE)));
                    vocab.setTimeSaved(cs.getString(cs.getColumnIndex(LocalDatabase.TIMESAVED)));

                    all.add(vocab);
//                    Log.e(" ----",all+"");
                }while (cs.moveToNext());
            }
        }
        catch (Exception e)
        {
            Log.d(e + "", "Error while trying to get posts from database");
        }

        Log.d("getAllBooks()", all.toString());
        //close cursor to free up cursor
        if (cs != null) {
            cs.close();
        }
        return all;
    }



    public int DELETE_FEED(int id) {
        // TODO Auto-generated method stub
       int result = wdb.delete(LocalDatabase.DATABASE_TABLE, LocalDatabase.ID+ "='"+id+"'", null);
        if (result==1){
          return result;
        }
        return 0;
    }

    public int UPDATE_FEED(int id_up, String title,int dateofpub,
                               String link,String description,
                               String image,String source) {
        // TODO Auto-generated method stub
        ContentValues cv = new ContentValues();
        cv.put(LocalDatabase.ID, id_up);
        cv.put(LocalDatabase.TITLE, title);
        cv.put(LocalDatabase.DATEOFPUBLICATION, dateofpub);
        cv.put(LocalDatabase.LINK, link);
        cv.put(LocalDatabase.DESCRIPTION, description);
        cv.put(LocalDatabase.IMAGEURL, image);
        cv.put(LocalDatabase.SOURCE, source);

        // cv.put(Database._DATE, new SimpleDateFormat("EEEE  dd-MM-yyyy   h:mm a").format(new Date()));
        int i = wdb.update(LocalDatabase.DATABASE_TABLE, cv, LocalDatabase.ID+ "='" + id_up+"'",null );
        wdb.close();
        return i;
    }



    public SavedFeed GET_SINGLE_FEED(int id){
        // 2. build query
        Cursor cs =
                rdb.query(LocalDatabase.DATABASE_TABLE, // a. table
                        LocalDatabase.COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cs != null)
            cs.moveToFirst();

        // 4. build  object
        SavedFeed vocab=new SavedFeed();
        assert cs != null;
        vocab.setId(cs.getInt(cs.getColumnIndex(LocalDatabase.ID)));
        vocab.setTitle(cs.getString(cs.getColumnIndex(LocalDatabase.TITLE)));
        vocab.setDateofpublication(cs.getString(cs.getColumnIndex(LocalDatabase.DATEOFPUBLICATION)));
        vocab.setLink(cs.getString(cs.getColumnIndex(LocalDatabase.LINK)));
        vocab.setDescription(cs.getString(cs.getColumnIndex(LocalDatabase.DESCRIPTION)));
        vocab.setImageUrl(cs.getString(cs.getColumnIndex(LocalDatabase.IMAGEURL)));
        vocab.setSource(cs.getString(cs.getColumnIndex(LocalDatabase.SOURCE)));
        vocab.setTimeSaved(cs.getString(cs.getColumnIndex(LocalDatabase.TIMESAVED)));

        //log
        Log.d("getFeed("+id+")", vocab.toString());
        //close cursor to free up cursor
        cs.close();
        // 5. return obj
        return vocab;
    }


    public int GET_SINGLE_FEED_usinglink(String link) {
        SavedFeed vocab=null;
        // 2. build query
        Cursor cs =
                rdb.query(LocalDatabase.DATABASE_TABLE, // a. table
                        LocalDatabase.COLUMNS, // b. column names
                        " link = ?", // c. selections
                        new String[] { link }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cs != null && cs.getCount()>0) {
            cs.moveToFirst();

            // 4. build book object
             vocab = new SavedFeed();
            vocab.setId(cs.getInt(cs.getColumnIndex(LocalDatabase.ID)));
            vocab.setTitle(cs.getString(cs.getColumnIndex(LocalDatabase.TITLE)));
            vocab.setDateofpublication(cs.getString(cs.getColumnIndex(LocalDatabase.DATEOFPUBLICATION)));
            vocab.setLink(cs.getString(cs.getColumnIndex(LocalDatabase.LINK)));
            vocab.setDescription(cs.getString(cs.getColumnIndex(LocalDatabase.DESCRIPTION)));
            vocab.setImageUrl(cs.getString(cs.getColumnIndex(LocalDatabase.IMAGEURL)));
            vocab.setSource(cs.getString(cs.getColumnIndex(LocalDatabase.SOURCE)));
            vocab.setTimeSaved(cs.getString(cs.getColumnIndex(LocalDatabase.TIMESAVED)));


            //log
            Log.d("getFeed(" + link + ")", vocab.toString());
        }
        //close cursor to free up cursor
        if (cs != null) {
            cs.close();
        }
        //return vocab;
        if (vocab != null) {
            return 1; //returned when not null
        }
        return 0;
    }


}
