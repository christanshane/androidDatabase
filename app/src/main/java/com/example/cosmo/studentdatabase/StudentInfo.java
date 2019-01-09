package com.example.cosmo.studentdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentInfo {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "student_name";
    public static final String KEY_PROGRAM = "academic_program";

    private static final String DATABASE_NAME = "StudentInfoDB";
    private static final String DATABASE_TABLE = "studentTable";
    private static final int DATABASE_VERSION = 3;

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    private static class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
                    + " TEXT NOT NULL, " + KEY_PROGRAM + " TEXT NOT NULL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public StudentInfo(Context c){
        ourContext = c;
    }

    public StudentInfo open() throws SQLException{
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        ourHelper.close();
    }

    public long createEntry(String name, String program){
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_PROGRAM, program);
        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public String getData(){
        String[] columns = new String[]{KEY_ROWID, KEY_NAME, KEY_PROGRAM};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result = "";

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iName = c.getColumnIndex(KEY_NAME);
        int iProgram = c.getColumnIndex(KEY_PROGRAM);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = result + c.getString(iRow) + " \t " + c.getString(iName) +  " \t " + c.getString(iProgram) +"\n";
        }
        if(result.equals("")){
            return "List is Empty!";
        }else{
            return result;
        }
    }

    public String getName(Long l){
        String[] columns = new String[]{KEY_ROWID, KEY_NAME, KEY_PROGRAM};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
        if(c !=null){
            c.moveToFirst();
            String program = c.getString(1);
            return program;
        }
        return null;
    }

    public String getProgram(Long l){
        String[] columns = new String[]{KEY_ROWID, KEY_NAME, KEY_PROGRAM};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
        if(c !=null){
            c.moveToFirst();
            String program = c.getString(2);
            return program;
        }
        return null;
    }

    public void updateEntry(Long lrow, String sname, String sprogram){
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_NAME, sname);
        cvUpdate.put(KEY_PROGRAM, sprogram);
        ourDatabase.update(DATABASE_TABLE, cvUpdate, KEY_ROWID + "=" + lrow, null);
    }

    public void deleteEntry(Long delrow){
        ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + delrow, null);
    }


}