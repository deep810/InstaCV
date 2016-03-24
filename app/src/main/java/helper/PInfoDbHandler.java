package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import database.CurrInfo;
import database.EduInfo;
import database.ItemStatus;
import database.PersonalInfo;
import database.ProjectInfo;
import database.RefInfo;
import database.SkillsInfo;

/**
 * Created by vishwashrisairm on 16/3/16.
 */
public class PInfoDbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "InstaCV"; //dbname
    private static final String TABLE_STATUS="ItemStatus";
    private static final String TABLE_PINFO = "PInfo";//table name
    private static final String TABLE_EINFO = "EInfo";
    private static final String TABLE_SINFO = "SInfo";
    private static final String TABLE_PRINFO = "PRInfo";
    private static final String TABLE_CINFO = "CInfo";
    private static final String TABLE_RINFO = "RInfo";


    public PInfoDbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_STATUS="CREATE TABLE "+TABLE_STATUS+"("
                +"item_id" +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"title"+" TEXT,"
                +"per_status"+" INTEGER,"
                +"edu_status"+" INTEGER,"
                +"pro_status"+" INTEGER,"
                +"skill_status"+" INTEGER,"
                +"excur_status"+" INTEGER,"
                +"ref_status"+" INTEGER"+")";



        String CREATE_PINFO_TABLE="CREATE TABLE "+TABLE_PINFO+"("
                +"id" +" INTEGER PRIMARY KEY,"
                +"name"+" TEXT,"
                +"address"+" TEXT,"
                +"dob"+" TEXT,"
                +"contact"+" TEXT,"
                +"email"+" TEXT,"
                +"objective"+" TEXT"+")";

        String CREATE_EINFO_TABLE="CREATE TABLE "+TABLE_EINFO+"("
                +"item_id"+" INTEGER,"
                +"edu_id" +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"degree"+" TEXT,"
                +"yop"+" TEXT,"
                +"cgpa"+" TEXT,"
                +"institute"+" TEXT,"
                +"FOREIGN KEY(item_id) REFERENCES "+TABLE_STATUS+"(item_id))";

        String CREATE_SINFO_TABLE="CREATE TABLE "+TABLE_SINFO+"("
                +"item_id"+" INTEGER,"
                +"skillid" +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"nameofskill"+" TEXT,"
                +"prof"+" TEXT,"
                +"FOREIGN KEY(item_id) REFERENCES "+TABLE_STATUS+"(item_id))";

        String CREATE_PRINFO_TABLE="CREATE TABLE "+TABLE_PRINFO+"("
                +"item_id"+" INTEGER,"
                +"proid" +" INTEGER PRIMARY KEY,"
                +"title"+" TEXT,"
                +"location"+" TEXT,"
                +"time"+" TEXT,"
                +"desig"+" TEXT,"
                +"desc"+" TEXT,"
                +"FOREIGN KEY(item_id) REFERENCES "+TABLE_STATUS+"(item_id))";

        String CREATE_CINFO_TABLE="CREATE TABLE "+TABLE_CINFO+"("
                +"item_id"+" INTEGER,"
                +"currid" +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"name"+" TEXT,"
                +"FOREIGN KEY(item_id) REFERENCES "+TABLE_STATUS+"(item_id))";

        String CREATE_RINFO_TABLE="CREATE TABLE "+TABLE_RINFO+"("
                +"item_id"+" INTEGER,"
                +"refid" +" INTEGER PRIMARY KEY,"
                +"rname"+" TEXT,"
                +"pos"+" TEXT,"
                +"contact"+" TEXT,"
                +"org"+" TEXT,"
                +"FOREIGN KEY(item_id) REFERENCES "+TABLE_STATUS+"(item_id))";



        db.execSQL(CREATE_TABLE_STATUS);
        db.execSQL(CREATE_PINFO_TABLE);
        db.execSQL(CREATE_EINFO_TABLE);
        db.execSQL(CREATE_SINFO_TABLE);
        db.execSQL(CREATE_PRINFO_TABLE);
        db.execSQL(CREATE_CINFO_TABLE);
        db.execSQL(CREATE_RINFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PINFO);
        onCreate(db);

        //Dekh lenge ...

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
        values.put("objective", p.get_objective());


        // Inserting Row
        db.insert(TABLE_PINFO, null, values);
        db.close(); // Closing database connection
    }

    //get single entry
    public PersonalInfo getPInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PINFO, new String[] { "id","name","address","dob"
                        ,"contact", "email", "objective" }, "id" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        PersonalInfo p = new PersonalInfo(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4)
        ,cursor.getString(5),cursor.getString(6));
        // return contact
        return p;
    }


    //get all pInfo by item_id

    //get all entries
    public List<PersonalInfo> getAllPInfoById(int i_id) {
        List<PersonalInfo> List = new ArrayList<PersonalInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PINFO + " WHERE id = "+i_id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

//        Log.i("cursor",cursor.toString());

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
                p.set_objective(cursor.getString(6));

                List.add(p);
            } while (cursor.moveToNext());
        }

        // return contact list
        return List;
    }


    //get all entries
    public List<PersonalInfo> getAllPInfo() {
        List<PersonalInfo> List = new ArrayList<PersonalInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PINFO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

//        Log.i("cursor",cursor.toString());

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
                p.set_objective(cursor.getString(6));

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
        values.put("objective", p.get_objective());

        // updating row
        return db.update(TABLE_PINFO, values, "id = ?",
                new String[]{String.valueOf(p.get_id())});
    }

    // Deleting single info
    public void deletePInfo(PersonalInfo p) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PINFO, " id= ?",
                new String[]{String.valueOf(p.get_id())});
        db.close();
    }

    // Getting  Count
    public int getPInfoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PINFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    // new table (education info)

    //Add values to the table
    public void addEInfo(EduInfo e) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("item_id",e.get_id());
    //    values.put("edu_id", e.get_eduid());
        values.put("degree", e.get_degree());
        values.put("yop", e.get_yop());
        values.put("cgpa", e.get_cgpa());
        values.put("institute", e.get_institute());



        // Inserting Row
        db.insert(TABLE_EINFO, null, values);
        db.close(); // Closing database connection
    }

    //get single entry
    public EduInfo getEInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EINFO, new String[] { "item_id","edu_id","degree","yop"
                        ,"cgpa", "institute" }, "edu_id" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        EduInfo e = new EduInfo(Integer.parseInt(cursor.getString(0)),
               cursor.getString(1), cursor.getString(2), cursor.getString(3)
                       , cursor.getString(4));
        // return contact
        return e;
    }

    // get all Einfo of same item_id
    public List<EduInfo> getAllEInfoByID(int i_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<EduInfo> List = new ArrayList<EduInfo>();
        Cursor cursor = db.query(TABLE_EINFO, new String[] { "item_id","edu_id","degree","yop"
                        ,"cgpa", "institute" }, "item_id" + "=?",
                new String[] { String.valueOf(i_id) }, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                EduInfo e = new EduInfo();
                e.set_id(Integer.parseInt(cursor.getString(0)));
                e.set_eduid(Integer.parseInt(cursor.getString(1)));
                e.set_degree(cursor.getString(2));
                e.set_yop(cursor.getString(3));
                e.set_cgpa(cursor.getString(4));
                e.set_institute(cursor.getString(5));


                List.add(e);
            }while(cursor.moveToNext());
        }
            //cursor.moveToFirst();
        return List;
    }

    //get all entries
    public List<EduInfo> getAllEInfo() {
        List<EduInfo> List = new ArrayList<EduInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EINFO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

//        Log.i("cursor",cursor.toString());

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EduInfo e = new EduInfo();
                e.set_id(Integer.parseInt(cursor.getString(0)));
                e.set_eduid(Integer.parseInt(cursor.getString(1)));
                e.set_degree(cursor.getString(2));
                e.set_yop(cursor.getString(3));
                e.set_cgpa(cursor.getString(4));
                e.set_institute(cursor.getString(5));


                List.add(e);
            } while (cursor.moveToNext());
        }

        // return contact list
        return List;
    }

    // Updating info
    public int updateEInfo(EduInfo e) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("edu_id", e.get_eduid());
        values.put("item_id",e.get_id());
        values.put("degree", e.get_degree());
        values.put("yop", e.get_yop());
        values.put("cgpa", e.get_cgpa());
        values.put("institute", e.get_institute());


        // updating row
        return db.update(TABLE_EINFO, values,  "edu_id = ?",
                new String[] { String.valueOf(e.get_eduid()) });
    }

    // Deleting single info
    public void deleteEInfo(EduInfo e) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EINFO, " edu_id= ?",
                new String[]{String.valueOf(e.get_eduid())});
        db.close();
    }

    // Getting contacts Count
    public int getEInfoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EINFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // new table skillsinfo

    /*
    * CRUD operations
    * */

    //Add values to the table
    public void addSInfo(SkillsInfo s) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("item_id", s.get_id());
        values.put("nameofskill", s.get_nameofskill());
        values.put("prof", s.get_prof());



        // Inserting Row
        db.insert(TABLE_SINFO, null, values);
        db.close(); // Closing database connection
    }

    //get single entry
    public SkillsInfo getSInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SINFO, new String[] { "item_id","skillid","nameofskill","prof"
                        }, "skillid" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        SkillsInfo s = new SkillsInfo(Integer.parseInt(cursor.getString(0)),
                 cursor.getString(1),cursor.getString(2));
        // return contact
        return s;
    }

    //get all skill of same item_id
    public List<SkillsInfo> getAllSInfoById(int i_id) {
        List<SkillsInfo> List = new ArrayList<SkillsInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SINFO +" WHERE item_id = "+i_id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

//        Log.i("cursor",cursor.toString());

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SkillsInfo s = new SkillsInfo();
                s.set_id(Integer.parseInt(cursor.getString(0)));
                s.set_skillid(Integer.parseInt(cursor.getString(1)));
                s.set_nameofskill(cursor.getString(2));
                s.set_prof(cursor.getString(3));



                List.add(s);
            } while (cursor.moveToNext());
        }

        // return contact list
        return List;
    }


    //get all entries
    public List<SkillsInfo> getAllSInfo() {
        List<SkillsInfo> List = new ArrayList<SkillsInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SINFO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

//        Log.i("cursor",cursor.toString());

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SkillsInfo s = new SkillsInfo();
                s.set_id(Integer.parseInt(cursor.getString(0)));
                s.set_skillid(Integer.parseInt(cursor.getString(1)));
                s.set_nameofskill(cursor.getString(2));
                s.set_prof(cursor.getString(3));



                List.add(s);
            } while (cursor.moveToNext());
        }

        // return contact list
        return List;
    }

    // Updating info
    public int updateSInfo(SkillsInfo s) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("skillid", s.get_skillid());
        values.put("nameofskill", s.get_nameofskill());
        values.put("prof", s.get_prof());


        // updating row
        return db.update(TABLE_SINFO, values,  "skillid = ?",
                new String[] { String.valueOf(s.get_skillid()) });
    }

    // Deleting single info
    public void deleteSInfo(SkillsInfo s) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SINFO, " skillid= ?",
                new String[]{String.valueOf(s.get_skillid())});
        db.close();
    }

    // Getting contacts Count
    public int getSInfoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SINFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }



    // new table (projects Info)



    /*
    * CRUD operations
    * */

    //Add values to the table
    public void addPRInfo(ProjectInfo pr) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("item_id", pr.get_id());
//        values.put("proid", pr.get_proid());
        values.put("title", pr.get_title());
        values.put("location", pr.get_time());
        values.put("time", pr.get_time());
        values.put("desig", pr.get_desig());
        values.put("desc", pr.get_desc());



        // Inserting Row
        db.insert(TABLE_PRINFO, null, values);
        db.close(); // Closing database connection
    }

    //get single entry
    public ProjectInfo getPRInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRINFO, new String[] { "item_id","proid","title","location","time","desig","desc"
                }, "proid" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ProjectInfo pr = new ProjectInfo(Integer.parseInt(cursor.getString(0)),
                 cursor.getString(1),cursor.getString(2), cursor.getString(3),cursor.getString(4), cursor.getString(5));
        // return contact
        return pr;
    }


    public List<ProjectInfo> getAllPRInfoById(int i_id) {
        List<ProjectInfo> List = new ArrayList<ProjectInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRINFO + " WHERE item_id = "+i_id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

//        Log.i("cursor",cursor.toString());

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProjectInfo pr = new ProjectInfo();
                pr.set_id(Integer.parseInt(cursor.getString(0)));
                pr.set_proid(Integer.parseInt(cursor.getString(1)));
                pr.set_title(cursor.getString(2));
                pr.set_location(cursor.getString(3));
                pr.set_time(cursor.getString(4));
                pr.set_desig(cursor.getString(5));
                pr.set_desc(cursor.getString(6));

                List.add(pr);
            } while (cursor.moveToNext());
        }

        // return contact list
        return List;
    }

    //get all entries
    public List<ProjectInfo> getAllPRInfo() {
        List<ProjectInfo> List = new ArrayList<ProjectInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRINFO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

//        Log.i("cursor",cursor.toString());

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProjectInfo pr = new ProjectInfo();
                pr.set_id(Integer.parseInt(cursor.getString(0)));
                pr.set_proid(Integer.parseInt(cursor.getString(1)));
                pr.set_title(cursor.getString(2));
                pr.set_location(cursor.getString(3));
                pr.set_time(cursor.getString(4));
                pr.set_desig(cursor.getString(5));
                pr.set_desc(cursor.getString(6));





                List.add(pr);
            } while (cursor.moveToNext());
        }

        // return contact list
        return List;
    }

    // Updating info
    public int updatePRInfo(ProjectInfo pr) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("item_id",pr.get_id());
        values.put("proid", pr.get_proid());
        values.put("title", pr.get_title());
        values.put("location", pr.get_location());
        values.put("time", pr.get_time());
        values.put("desig", pr.get_desig());
        values.put("desc", pr.get_desc());


        // updating row
        return db.update(TABLE_PRINFO, values,  "proid = ?",
                new String[] { String.valueOf(pr.get_proid()) });
    }

    // Deleting single info
    public void deletePRInfo(ProjectInfo pr) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRINFO, " proid= ?",
                new String[]{String.valueOf(pr.get_proid())});
        db.close();
    }

    // Getting contacts Count
    public int getPRInfoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PRINFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // new table (Curriculum Info)



    /*
    * CRUD operations
    * */

    //Add values to the table
    public void addCInfo(CurrInfo c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
       values.put("item_id", c.get_id());
        values.put("name", c.get_name());




        // Inserting Row
        db.insert(TABLE_CINFO, null, values);
        db.close(); // Closing database connection
    }

    //get single entry
    public CurrInfo getCInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CINFO, new String[] { "item_id","currid","name"
                }, "currid" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        CurrInfo pr = new CurrInfo(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        // return contact
        return pr;
    }

   //get Extra-curr info by item_id
    public List<CurrInfo> getAllCInfoById(int i_id) {
        List<CurrInfo> List = new ArrayList<CurrInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CINFO + " WHERE item_id = " + i_id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

//        Log.i("cursor",cursor.toString());

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CurrInfo c = new CurrInfo();
                c.set_id(Integer.parseInt(cursor.getString(0)));
                c.set_currid(Integer.parseInt(cursor.getString(1)));
                c.set_name(cursor.getString(2));

                List.add(c);
            } while (cursor.moveToNext());
        }

        // return contact list
        return List;
    }


    //get all entries
    public List<CurrInfo> getAllCInfo() {
        List<CurrInfo> List = new ArrayList<CurrInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CINFO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

//        Log.i("cursor",cursor.toString());

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CurrInfo c = new CurrInfo();
                c.set_id(Integer.parseInt(cursor.getString(0)));
                c.set_currid(Integer.parseInt(cursor.getString(1)));
                c.set_name(cursor.getString(2));

                List.add(c);
            } while (cursor.moveToNext());
        }

        // return contact list
        return List;
    }

    // Updating info
    public int updateCInfo(CurrInfo c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("currid", c.get_currid());
        values.put("name", c.get_name());


        // updating row
        return db.update(TABLE_CINFO, values,  "currid = ?",
                new String[] { String.valueOf(c.get_currid()) });
    }

    // Deleting single info
    public void deleteCInfo(CurrInfo c) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CINFO, " currid= ?",
                new String[]{String.valueOf(c.get_currid())});
        db.close();
    }

    // Getting contacts Count
    public int getCInfoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CINFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    // new table (Curriculum Info)



    /*
    * CRUD operations
    * */

    //Add values to the table
    public void addRInfo(RefInfo r) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("item_id", r.get_id());
        values.put("rname", r.get_rname());
        values.put("pos", r.get_pos());
        values.put("contact", r.get_contact());
        values.put("org", r.get_org());





        // Inserting Row
        db.insert(TABLE_RINFO, null, values);
        db.close(); // Closing database connection
    }

    //get single entry
    RefInfo getRInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RINFO, new String[] { "item_id","refid","rname","pos","contact","org"
                }, "refid" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        RefInfo r = new RefInfo(Integer.parseInt(cursor.getString(0)),
                 cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return contact
        return r;
    }
    //get all reference by item_id
    public List<RefInfo> getAllRInfoById(int i_id) {
        List<RefInfo> List = new ArrayList<RefInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RINFO + " WHERE item_id = "+i_id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

//        Log.i("cursor",cursor.toString());

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                RefInfo r = new RefInfo();
                r.set_id(Integer.parseInt(cursor.getString(0)));
                r.set_refid(Integer.parseInt(cursor.getString(1)));
                r.set_rname(cursor.getString(2));
                r.set_pos(cursor.getString(3));
                r.set_contact(cursor.getString(4));
                r.set_org(cursor.getString(5));



                List.add(r);
            } while (cursor.moveToNext());
        }

        // return contact list
        return List;
    }

    //get all entries
    public List<RefInfo> getAllRInfo() {
        List<RefInfo> List = new ArrayList<RefInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RINFO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

//        Log.i("cursor",cursor.toString());

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                RefInfo r = new RefInfo();
                r.set_id(Integer.parseInt(cursor.getString(0)));
                r.set_refid(Integer.parseInt(cursor.getString(1)));
                r.set_rname(cursor.getString(2));
                r.set_pos(cursor.getString(3));
                r.set_contact(cursor.getString(4));
                r.set_org(cursor.getString(5));



                List.add(r);
            } while (cursor.moveToNext());
        }

        // return contact list
        return List;
    }

    // Updating info
    public int updateRInfo(RefInfo r) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("item_id",r.get_id());
        values.put("refid", r.get_refid());
        values.put("rname", r.get_rname());
        values.put("pos", r.get_pos());
        values.put("contact", r.get_contact());
        values.put("org", r.get_org());


        // updating row
        return db.update(TABLE_RINFO, values,  "refid = ?",
                new String[] { String.valueOf(r.get_refid()) });
    }

    // Deleting single info
    public void deleteRInfo(RefInfo r) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RINFO, " refid= ?",
                new String[]{String.valueOf(r.get_refid())});
        db.close();
    }

    // Getting contacts Count
    public int getRInfoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_RINFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }



//ItemStatus table

       /*
    * CRUD operations
    * */

    //Add values to the table
    public int addStatus(ItemStatus r) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put("item_id", r.get_item_id());
        values.put("title", r.getTitle());
        values.put("per_status", r.get_personalstatus());
        values.put("edu_status", r.get_edustatus());
        values.put("pro_status", r.get_prostatus());
        values.put("skill_status", r.get_skillstatus());
        values.put("excur_status", r.get_excurstatus());
        values.put("ref_status", r.get_refstatus());





        // Inserting Row
        int id =(int) db.insert(TABLE_STATUS, null, values);
        db.close(); // Closing database connection
        return id;
    }

    //get single entry
    public ItemStatus getStatus(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STATUS, new String[] { "item_id","title","per_status","edu_status","pro_status","skill_status","excur_status"
              ,"ref_status" }, "item_id" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ItemStatus item = new ItemStatus(cursor.getString(1),Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)),Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(5)),Integer.parseInt(cursor.getString(6))
        ,Integer.parseInt(cursor.getString(7)));
        // return contact
        return item;
    }

    //get all entries
    public List<ItemStatus> getAllStatus() {
        List<ItemStatus> List = new ArrayList<ItemStatus>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STATUS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

//        Log.i("cursor",cursor.toString());

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemStatus r = new ItemStatus();
                r.set_item_id(Integer.parseInt(cursor.getString(0)));
                r.set_personalstatus(Integer.parseInt(cursor.getString(2)));
                r.setTitle(cursor.getString(1));
                r.set_edustatus(Integer.parseInt(cursor.getString(3)));
                r.set_prostatus(Integer.parseInt(cursor.getString(4)));
                r.set_skillstatus(Integer.parseInt(cursor.getString(5)));
                r.set_excurstatus(Integer.parseInt(cursor.getString(6)));
                r.set_refstatus(Integer.parseInt(cursor.getString(7)));

                List.add(r);
            } while (cursor.moveToNext());
        }

        // return contact list
        return List;
    }

    // Updating info
    public int updateStatus(ItemStatus r) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("item_id", r.get_item_id());
        values.put("title", r.getTitle());
        values.put("title", r.getTitle());
        values.put("per_status", r.get_personalstatus());
        values.put("edu_status", r.get_edustatus());
        values.put("pro_status", r.get_prostatus());
        values.put("skill_status", r.get_skillstatus());
        values.put("excur_status", r.get_excurstatus());
        values.put("ref_status", r.get_refstatus());



        // updating row
        return db.update(TABLE_STATUS, values,  "item_id = ?",
                new String[] { String.valueOf(r.get_item_id()) });
    }

    // Deleting single info
    public void deleteStatus(ItemStatus r) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STATUS, " item_id= ?",
                new String[]{String.valueOf(r.get_item_id())});
        db.close();
    }

    // Getting contacts Count
    public int getStatusCount() {
        String countQuery = "SELECT  * FROM " + TABLE_STATUS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }



}
