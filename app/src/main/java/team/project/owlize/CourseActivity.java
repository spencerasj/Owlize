package team.project.owlize;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {
    private static final String TAG = "CourseActivity";
    private ArrayList<String> courses;
    private ArrayAdapter<String> coursesAdapter;
    private ListView courseList;
    EditText course;

    DBHelper myDB;
    private String selectedSubject;
    private int selectedID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        myDB = new DBHelper(this);

        courseList = findViewById(R.id.assignmentList);
        courses = new ArrayList<>();

        populateCourseList();
//        Log.d(TAG, "Received intent with Course");
//        readItems();
//        coursesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courses);
//        courseList.setAdapter(coursesAdapter);
//        courseList.setOnItemClickListener((adapterView, view, i, id) -> {
//            String course = adapterView.getItemAtPosition(i).toString();
//            Intent assignIntent = new Intent(CourseActivity.this, AssignmentActivity.class);
//            assignIntent.putExtra("CourseListItem", course);
//            startActivity(assignIntent);
//
//        });
//        Log.d(TAG, "Received intent with favScripture");
        longClickDelete();
    }

    private void populateCourseList() {
        Log.d(TAG, "populateSubjectList: Displaying subjects in the ListView.");
        Cursor subjects = myDB.getAllData();
        ArrayList<String> listSubjects = new ArrayList<>();
        while (subjects.moveToNext()){
            listSubjects.add(subjects.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item,listSubjects);
        courseList.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String subject = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + subject);

                Cursor data = myDB.getItemID(subject); //get the id associated with that name
                int itemID = -1;
                while (data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if (itemID > -1){
                    Log.d(TAG,"onItemClick: The ID is: " + itemID);
                    Intent taskOverview = new Intent(CourseActivity.this, AssignmentActivity.class);
                    taskOverview.putExtra("id",itemID);
                    taskOverview.putExtra("name",subject);
                    startActivity(taskOverview);
                } else {
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }



    private void longClickDelete() {
        courseList.setOnItemLongClickListener(
                (adapter, item, pos, id) -> {
                    String courseName = adapter.getItemAtPosition(pos).toString();
                    Integer courseID = myDB.getCoursesId(courseName);
                    myDB.deleteCourse(courseID.toString());
                    populateCourseList();
                    return true;
                });
    }

    public void addCourse(View view) {
        course = findViewById(R.id.courseNameEditText);
        String courseName = course.getText().toString();
        if (courseName.length() == 0) {
            toastMessage("Please enter a course name");
            return;
        }

        AddData(courseName);
        populateCourseList();
        course.setText("");
    }

    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

//    public void receiveCourse(View view) {
//

    public void AddData(String subjEntry){
        myDB.insertCourse(subjEntry);
    }


}
