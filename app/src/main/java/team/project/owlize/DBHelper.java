package team.project.owlize;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";

    // columns of the subject table
    private static final String COURSES_TABLE = "course_table";
    private static final String COURSES_ID = "course_ID";
    private static final String COURSES_NAME = "course_NAME";

    // columns of the task table
    private static final String ASSIGNMENT_TABLE = "assignment_table";
    private static final String ASSIGNMENT_ID = "assignment_id";
    private static final String ASSIGNMENT_DESC = "assignment_desc";
    private static final String ASSIGNMENT_DATE = "assignment_date";
    private static final String ASSIGNMENT_SUBJECT_ID = "course_id_fk";

    private static final String DATABASE_NAME = "courses.db";
    private static final int DATABASE_VERSION = 1;

    //SQL statement of the subjects table creation
    private static final String SQL_CREATE_TABLE_SUBJECTS = "CREATE TABLE " + COURSES_TABLE + " ("
            + COURSES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COURSES_NAME + " VARCHAR NOT NULL )";

    //SQL statement of the task table creation
    private static final String SQL_CREATE_TABLE_TASKS = "CREATE TABLE " + ASSIGNMENT_TABLE + " ("
            + ASSIGNMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ASSIGNMENT_SUBJECT_ID + " INTEGER, "
            + ASSIGNMENT_DESC + " TEXT, "
            + ASSIGNMENT_DATE + " TEXT, "
            + "FOREIGN KEY(" + ASSIGNMENT_SUBJECT_ID + ") REFERENCES " + COURSES_TABLE + " (" + COURSES_ID + "));";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_SUBJECTS);
        db.execSQL(SQL_CREATE_TABLE_TASKS);
    }

    //If the database is upgrade it drops the tables and readdds them
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + ASSIGNMENT_TABLE);
        db.execSQL("drop table if exists " + COURSES_TABLE);
        onCreate(db);
    }
    //Inserts Course into the table
    public void insertCourse(String subject) {
        SQLiteDatabase db = this.getWritableDatabase(); // just for testing
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSES_NAME,subject);

        Log.d(TAG, "insertSubject: Adding " + subject + " to " + COURSES_TABLE);

        long result = db.insert(COURSES_TABLE,null,contentValues);
    }
    //Inserts the Assignments into the table
    public boolean insertAssignment(String dbTaskDate, String dbTaskDesc, int dbSubjID) {
        SQLiteDatabase db = this.getWritableDatabase(); // just for testing
        ContentValues contentValues = new ContentValues();
        contentValues.put(ASSIGNMENT_SUBJECT_ID,dbSubjID);
        contentValues.put(ASSIGNMENT_DESC,dbTaskDesc);
        contentValues.put(ASSIGNMENT_DATE,dbTaskDate);

        Log.d(TAG, "insertSubject: Adding " + dbTaskDesc + " to " + ASSIGNMENT_TABLE);

        long result = db.insert(ASSIGNMENT_TABLE,null,contentValues);
        db.close();
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + COURSES_TABLE;
        return db.rawQuery(query,null);
    }
    //retrieves assignment data
    public Cursor getAssignmentData(int subj_id){

        String sub_id = Integer.toString(subj_id);

        SQLiteDatabase db = this.getWritableDatabase();

        return db.query(ASSIGNMENT_TABLE,
                new String[] {ASSIGNMENT_ID, ASSIGNMENT_DESC, ASSIGNMENT_DATE},
                ASSIGNMENT_SUBJECT_ID + " = '"+sub_id+"'",
                null,
                null,
                null,
                ASSIGNMENT_DATE + " ASC");
    }

    public Cursor getSingleTask(String task_id){

        SQLiteDatabase db = this.getWritableDatabase();

        return db.query(ASSIGNMENT_TABLE,
                new String[] {ASSIGNMENT_DESC, ASSIGNMENT_DATE},
                ASSIGNMENT_ID + " = '"+task_id+"'",
                null,
                null,
                null,
                null);
    }

    // Returns only the ID that matches the subject passed in
    public Cursor getItemID(String subj_name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COURSES_ID  + " FROM " + COURSES_TABLE +
                " WHERE " + COURSES_NAME + " = '" + subj_name + "'";
        return db.rawQuery(query, null);
    }

    public int getCoursesId(String course_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COURSES_ID + " FROM " + COURSES_TABLE +
                " WHERE " + COURSES_NAME + " = '" + course_name + "'";
        Cursor getCourse = db.rawQuery(query,null);
        getCourse.moveToFirst();
        return getCourse.getInt(0);
//        return String.valueOf(db.query(COURSES_TABLE,
//                new String [] { COURSES_NAME },
//                COURSES_ID + " = '"+course_name+"'",
//                null,
//                null,
//                null,
//                null));
    }
    // Deletes assignments from table
    public void deleteAssignment(String taskID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ASSIGNMENT_TABLE, ASSIGNMENT_ID + " = ?", new String[]{taskID});
    }

    public boolean updateTask(String subj_id, String task_id, String task_desc, String task_date ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ASSIGNMENT_ID,task_id);
        contentValues.put(ASSIGNMENT_DESC,task_desc);
        contentValues.put(ASSIGNMENT_DATE,task_date);
        db.update(ASSIGNMENT_TABLE, contentValues, ASSIGNMENT_ID +" = ? AND " + ASSIGNMENT_SUBJECT_ID+" = ?", new String[] {task_id,subj_id});
        return true;
    }
    // Deletes courses from table
    public void deleteCourse(String courseID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ASSIGNMENT_TABLE, ASSIGNMENT_SUBJECT_ID + " = ?", new String[]{courseID});
        db.delete(COURSES_TABLE, COURSES_ID + " = ?", new String[]{courseID});
    }
}
