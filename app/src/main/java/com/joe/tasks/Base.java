package com.joe.tasks;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
public class Base extends SQLiteOpenHelper {
    public Base(@Nullable Context context) {
        super(context, "TasksDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE task(id INTEGER PRIMARY KEY,textTask TEXT,isCompleted INTEGER)");
        db.execSQL("CREATE TABLE taskDone(id INTEGER PRIMARY KEY,textTask TEXT,date DATE)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS task");
        onCreate(db);
    }
    public  void insetTask(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c= new ContentValues();
        c.put("textTask",text);
        c.put("isCompleted",0);
        db.insert("task",null,c);
        db.close();
    }
    public Cursor getAllTask(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM task ORDER BY isCompleted",null);
        return cur;
    }
    public void deleteTask(int id){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete("task","id=?",new String[]{String.valueOf(id)});
        db.close();
    }
    public void markCompleted(int idTask){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("isCompleted",1);
        db.update("task",c,"id=?",new String[]{String.valueOf(idTask)});
        // add the task to taskDone
        Cursor cur = db.rawQuery("SELECT * FROM task WHERE id=?",new String[]{String.valueOf(idTask)});
        cur.moveToNext();
        String textTask = cur.getString(cur.getColumnIndexOrThrow("textTask"));
        ContentValues c2 = new ContentValues();
        c2.put("textTask",textTask);
        c2.put("date",String.valueOf(java.time.LocalDate.now()));
        db.insert("taskDone",null,c2);
        db.close();
    }

    public Cursor getAllTaskDone(String date){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM taskDone WHERE date=?",new String[]{date});
        return cur;
    }

   // showTaskDone
public Cursor showTaskDone(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM taskDone",null);
        return cur;
    }

}