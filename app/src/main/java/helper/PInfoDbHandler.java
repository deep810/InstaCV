package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import database.PersonalInfo;

/**
 * Created by vishwashrisairm on 16/3/16.
 */
public class PInfoDbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "InstaCV"; //dbname
    private static final String TABLE_PINFO = "PInfo";//table name


    public PInfoDbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PINFO_TABLE="CREATE TABLE "+TABLE_PINFO+"("
                +"id" +" INTEGER PRIMARY KEY,"
                +"name"+" TEXT,"
                +"address"+" TEXT,"
                +"dob"+" TEXT,"
                +"contact"+" TEXT,"
                +"email"+" TEXT"+")";
        db.execSQL(CREATE_PINFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PINFO);
        onCreate(db);

    }

    /*
    * CRUD operations
    * */

    //Add values to the table
    public void addPInfo(PersonalInfo p) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", p.get_fullname());
        values.put("address", p.get_address());
        values.put("dob", p.get_dob());
        values.put("contact", p.get_contact());
        values.put("email", p.get_email());


        // Inserting Row
        db.insert(TABLE_PINFO, null, values);
        db.close(); // Closing database connection
    }

    //get single entry
    PersonalInfo getPInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PINFO, new String[] { "id","name","address","dob"
                        ,"contact", "email" }, "id" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        PersonalInfo p = new PersonalInfo(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4)
        ,cursor.getString(5));
        // return contact
        return p;
    }

    //get all entries
    public List<PersonalInfo> getAllPInfo() {
        List<PersonalInfo> List = new ArrayList<PersonalInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PINFO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PersonalInfo p = new PersonalInfo();
                p.set_id(Integer.parseInt(cursor.getString(0)));
                p.set_fullname(cursor.getString(1));
                p.set_address(cursor.getString(2));
                p.set_dob(cursor.getString(3));
                p.set_contact(cursor.getString(4));
                p.set_email(cursor.getString(5));

                List.add(p);
            } while (cursor.moveToNext());
        }

        // return contact list
        return List;
    }

    // Updating info
    public int updatePInfo(PersonalInfo p) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", p.get_fullname());
        values.put("address", p.get_address());
        values.put("dob", p.get_dob());
        values.put("contact", p.get_contact());
        values.put("email", p.get_email());

        // updating row
        return db.update(TABLE_PINFO, values,  "id = ?",
                new String[] { String.valueOf(p.get_id()) });
    }

    // Deleting single info
    public void deletePInfo(PersonalInfo p) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PINFO, " id= ?",
                new String[]{String.valueOf(p.get_id())});
        db.close();
    }

    // Getting contacts Count
    public int getPInfoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PINFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
